package wedoevents.eventplanner.eventManagement.serviceBookingTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import wedoevents.eventplanner.eventManagement.dtos.*;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;
import wedoevents.eventplanner.eventManagement.services.ServiceBudgetItemService;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;
import wedoevents.eventplanner.serviceManagement.repositories.VersionedServiceRepository;
import wedoevents.eventplanner.shared.Exceptions.BuyServiceException;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceBookingServiceTests {
    @Mock
    private ServiceBudgetItemRepository serviceBudgetItemRepository;
    @Mock
    private VersionedServiceRepository versionedServiceRepository;
    @Mock
    private ServiceCategoryRepository serviceCategoryRepository;
    @Mock
    private EventOrganizerRepository eventOrganizerRepository;
    @Mock
    private EventRepository eventRepository;

    private ServiceBudgetItemService service;

    private Event mockEvent;
    private EventType mockEventType;
    private StaticService mockStaticService;
    private EventOrganizer mockEventOrganizer;
    private VersionedService mockVersionedService;
    private List<ServiceBudgetItem> sbiStorage;

    @BeforeAll
    public void setupClass() {
        MockitoAnnotations.openMocks(this);

        mockEventOrganizer = new EventOrganizer();
        mockEventOrganizer.setId(UUID.randomUUID());

        ServiceCategory mockServiceCategory = new ServiceCategory();
        mockServiceCategory.setId(UUID.randomUUID());

        mockStaticService = new StaticService();
        mockStaticService.setStaticServiceId(UUID.randomUUID());
        mockStaticService.setServiceCategory(mockServiceCategory);

        mockEventType = new EventType();
        mockEventType.setId(UUID.randomUUID());

        mockVersionedService = new VersionedService();
        mockVersionedService.setStaticService(mockStaticService);
        mockVersionedService.setStaticServiceId(mockStaticService.getStaticServiceId());

        mockEvent = new Event();
        mockEvent.setEventType(mockEventType);

        sbiStorage = new ArrayList<>();

        when(versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(any())).thenReturn(Optional.of(mockVersionedService));

        when(eventOrganizerRepository.getMyEventsById(any())).thenReturn(List.of(mockEvent));

        when(eventRepository.findById(mockEvent.getId())).thenReturn(Optional.of(mockEvent));

        when(serviceBudgetItemRepository.doesOverlap(any(), any(), any())).thenAnswer(invocation -> {
            LocalDateTime start = invocation.getArgument(1), end = invocation.getArgument(2);
            return sbiStorage.stream().anyMatch(sbi -> sbi.getService() != null && sbi.getService().getStaticServiceId().equals(invocation.getArgument(0))
                                                       && (sbi.getStartTime().isBefore(end) || sbi.getStartTime().isEqual(end))
                                                       && (sbi.getEndTime().isAfter(start) || sbi.getEndTime().isEqual(start)));
        });

        when(serviceBudgetItemRepository.getForServiceAndDay(any(), any())).thenAnswer(invocation -> {
            return sbiStorage.stream().filter(sbi -> sbi.getService().getStaticServiceId().equals(invocation.getArgument(0))
                                                     && sbi.getStartTime().toLocalDate().isEqual(invocation.getArgument(1))).toList();
        });

        when(serviceBudgetItemRepository.save(any(ServiceBudgetItem.class))).thenAnswer((Answer<ServiceBudgetItem>) invocation -> {
            ServiceBudgetItem item = invocation.getArgument(0);
            item.setId(UUID.randomUUID());
            sbiStorage.add(item);
            return item;
        });

        when(serviceBudgetItemRepository.findById(any(UUID.class))).thenAnswer((Answer<Optional<ServiceBudgetItem>>) invocation ->
                sbiStorage.stream().filter(s -> s.getId().equals(invocation.getArgument(0))).findFirst());

        when(serviceCategoryRepository.findById(mockServiceCategory.getId())).thenReturn(Optional.of(mockServiceCategory));

        service = new ServiceBudgetItemService(
                serviceBudgetItemRepository,
                versionedServiceRepository,
                serviceCategoryRepository,
                eventOrganizerRepository,
                eventRepository
        );
    }

    @BeforeEach
    public void resetState() {
        mockVersionedService.setIsActive(true);
        mockVersionedService.setIsPrivate(false);
        mockVersionedService.setIsAvailable(true);

        mockEvent.setDate(LocalDate.now().plusDays(10));
        mockEvent.setServiceBudgetItems(new ArrayList<>());

        mockVersionedService.setAvailableEventTypes(List.of(mockEventType));
        mockVersionedService.setMaximumDuration(100);
        mockVersionedService.setMinimumDuration(35);
        mockVersionedService.setSalePercentage(0.1);
        mockVersionedService.setPrice(100.0);

        sbiStorage.clear();
    }

    @Test
    void testSlotsSuccesfull() {
        Random random = new Random();
        int tryTimes = 500;

        for (int loop = 0; loop < tryTimes; loop++) {
            mockVersionedService.setMinimumDuration(random.nextInt(1, 60 * 24));
            mockVersionedService.setMaximumDuration(random.nextInt(mockVersionedService.getMinimumDuration(), 60 * 24));

            List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
            HashMap<String, List<String>> timeTable = slots.get(0).getTimeTable();

            assertEquals(1, slots.size());

            int startTimesCount = 24 * 4 + 1 - (int) Math.ceil(mockVersionedService.getMinimumDuration() / 15.0);
            int endTimesCount = (int) Math.floor((mockVersionedService.getMaximumDuration() - mockVersionedService.getMinimumDuration()) / 15.0) + 1;

            assertEquals(startTimesCount, timeTable.size());
            assertEquals(startTimesCount - endTimesCount + 1, timeTable.values().stream().filter(endTimes -> endTimes.size() == endTimesCount).count());
            for (int i = 1; i < endTimesCount; i++) {
                int finalI = i;
                assertEquals(1, timeTable.values().stream().filter(endTimes -> endTimes.size() == finalI).count());
            }
        }
    }

    @Test
    void testSlotsWithInactiveService() {
        mockVersionedService.setIsActive(false);

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertTrue(slots.isEmpty());
    }

    @Test
    void testSlotsWithPrivateService() {
        mockVersionedService.setIsPrivate(true);

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertTrue(slots.isEmpty());
    }

    @Test
    void testSlotsWithUnavailableService() {
        mockVersionedService.setIsAvailable(false);

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertTrue(slots.isEmpty());
    }

    @Test
    void testSlotsIfEventPassed() {
        mockEvent.setDate(LocalDate.now().minusDays(1));

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertTrue(slots.isEmpty());
    }

    @Test
    void testSlotsIfEventTypeNotAvailable() {
        mockVersionedService.setAvailableEventTypes(new ArrayList<>());

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertTrue(slots.isEmpty());
    }

    @Test
    void testSlotsWhenBudgetItemNotPresent() {
        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertFalse(slots.isEmpty());
    }

    @Test
    void testSlotsWhenBudgetItemReserved() {
        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setServiceCategory(mockVersionedService.getStaticService().getServiceCategory());
        item.setService(new VersionedService());
        mockEvent.setServiceBudgetItems(List.of(item));

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertTrue(slots.isEmpty());
    }

    @Test
    void testSlotsWhenBudgetItemTooExpensive() {
        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setServiceCategory(mockVersionedService.getStaticService().getServiceCategory());
        item.setMaxPrice(50.0);
        mockEvent.setServiceBudgetItems(List.of(item));

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertTrue(slots.isEmpty());
    }

    @Test
    void testSlotsWhenBudgetItemCheapEnough() {
        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setServiceCategory(mockVersionedService.getStaticService().getServiceCategory());
        item.setMaxPrice(150.0);
        mockEvent.setServiceBudgetItems(List.of(item));

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertFalse(slots.isEmpty());
    }

    @Test
    void testSlotsWhenBudgetItemCheapEnoughOnlyWithSalePercantage() {
        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setServiceCategory(mockVersionedService.getStaticService().getServiceCategory());
        item.setMaxPrice(95.0);
        mockEvent.setServiceBudgetItems(List.of(item));

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertFalse(slots.isEmpty());

        mockVersionedService.setSalePercentage(0.0);

        slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        assertTrue(slots.isEmpty());
    }

    @Test
    void testSlotsWhenMinMaxDurationIsEqual() {
        mockVersionedService.setMinimumDuration(120);
        mockVersionedService.setMaximumDuration(120);

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        HashMap<String, List<String>> timeTable = slots.get(0).getTimeTable();

        assertEquals(22 * 4 + 1, timeTable.size());

        for (int i = 0; i <= 22; i++) {
            for (int j = 0; j < 60; j += 15) {
                String startTime = String.format("%02d:%02d", i, j);
                String endTime = String.format("%02d:%02d", i + 2 == 24 ? 0 : i + 2, j);
                assertTrue(timeTable.containsKey(startTime));
                assertEquals(1, timeTable.get(startTime).size());
                assertEquals(endTime, timeTable.get(startTime).get(0));
                if (startTime.equals("22:00")) return;
            }
        }
    }

    @Test
    void testSlotsWhenSlotInMiddleIsReserved() {
        mockVersionedService.setMinimumDuration(10);
        mockVersionedService.setMaximumDuration(150);

        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setService(mockVersionedService);
        item.setStartTime(LocalDateTime.of(mockEvent.getDate(), LocalTime.of(3, 0)));
        item.setEndTime(LocalDateTime.of(mockEvent.getDate(), LocalTime.of(6, 0)));
        sbiStorage.add(item);

        item = new ServiceBudgetItem();
        item.setService(mockVersionedService);
        item.setStartTime(LocalDateTime.of(mockEvent.getDate(), LocalTime.of(18, 0)));
        item.setEndTime(LocalDateTime.of(mockEvent.getDate(), LocalTime.of(19, 0)));
        sbiStorage.add(item);

        List<BookingSlotsDTO> slots = service.getSlots(mockStaticService.getStaticServiceId(), mockEventOrganizer.getId());
        HashMap<String, List<String>> timeTable = slots.get(0).getTimeTable();

        timeTable.keySet().forEach(startTime -> {
            assertNotEquals(3, Integer.parseInt(startTime.split(":")[0]));
            assertNotEquals(4, Integer.parseInt(startTime.split(":")[0]));
            assertNotEquals(5, Integer.parseInt(startTime.split(":")[0]));
            assertNotEquals(18, Integer.parseInt(startTime.split(":")[0]));

            timeTable.get(startTime).forEach(endTime -> {
                if (!endTime.equals("03:00")) assertNotEquals(3, Integer.parseInt(endTime.split(":")[0]));
                assertNotEquals(4, Integer.parseInt(endTime.split(":")[0]));
                assertNotEquals(5, Integer.parseInt(endTime.split(":")[0]));
                if (!endTime.equals("18:00")) assertNotEquals(18, Integer.parseInt(endTime.split(":")[0]));
            });
        });
    }

    @Test
    void testBuySuccessful() {
        ServiceBudgetItemDTO dto = service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00"));
        assertNotNull(dto.getId());
    }

    @Test
    void testBuyWithInactiveService() {
        mockVersionedService.setIsActive(false);

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertEquals("Can't buy service that is not visible to you", exception.getMessage());
    }

    @Test
    void testBuyWithPrivateService() {
        mockVersionedService.setIsPrivate(true);

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertEquals("Can't buy service that is not visible to you", exception.getMessage());
    }

    @Test
    void testBuyWithUnavailableService() {
        mockVersionedService.setIsAvailable(false);

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertEquals("Can't buy service that is not visible to you", exception.getMessage());
    }

    @Test
    void testBuyIfEventTypeNotAvailable() {
        mockVersionedService.setAvailableEventTypes(new ArrayList<>());

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertEquals("Event type not in event's available event types", exception.getMessage());
    }

    @Test
    void testBuyIfEventPassed() {
        mockEvent.setDate(LocalDate.now().minusDays(1));

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertEquals("Event has passed", exception.getMessage());
    }

    @Test
    void testBuyIfEventAlreadyBookedThatServiceCategory() {
        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setServiceCategory(mockVersionedService.getStaticService().getServiceCategory());
        item.setService(new VersionedService());
        item.setMaxPrice(9999);
        mockEvent.setServiceBudgetItems(List.of(item));

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertEquals("Service budget item for that event and category is already booked", exception.getMessage());
    }

    @Test
    void testBuyIfServiceIsBookedAtThatTime() {
        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setStartTime(LocalDateTime.of(mockEvent.getDate(), LocalTime.parse("02:30")));
        item.setEndTime(LocalDateTime.of(mockEvent.getDate(), LocalTime.parse("03:30")));
        item.setService(mockVersionedService);
        sbiStorage.add(item);

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertEquals("Service is unavailable at that time", exception.getMessage());
    }

    @Test
    void testBuyMultipleTimes() {
        assertDoesNotThrow(() -> service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertThrows(BuyServiceException.class, () -> service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
    }

    @Test
    void testBuyWhenBudgetItemTooExpensive() {
        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setServiceCategory(mockVersionedService.getStaticService().getServiceCategory());
        item.setMaxPrice(50.0);
        mockEvent.setServiceBudgetItems(List.of(item));

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertEquals("Service too expensive", exception.getMessage());
    }

    @Test
    void testBuyWhenBudgetItemCheapEnough() {
        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setServiceCategory(mockVersionedService.getStaticService().getServiceCategory());
        item.setMaxPrice(150.0);
        mockEvent.setServiceBudgetItems(List.of(item));

        assertDoesNotThrow(() -> service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
    }

    @Test
    void testBuyWhenBudgetItemCheapEnoughOnlyWithSalePercantage() {
        ServiceBudgetItem item = new ServiceBudgetItem();
        item.setServiceCategory(mockVersionedService.getStaticService().getServiceCategory());
        item.setMaxPrice(95.0);
        mockEvent.setServiceBudgetItems(List.of(item));

        assertDoesNotThrow(() -> service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));

        item.setService(null);
        mockVersionedService.setSalePercentage(0.0);

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "13:00", "14:00")));
        assertEquals("Service too expensive", exception.getMessage());
    }

    @Test
    void testBuyWhenDurationAboveMax() {
        mockVersionedService.setMaximumDuration(90);

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "06:00")));
        assertEquals("The service's reservation must be at most 90 minutes", exception.getMessage());
    }


    @Test
    void testBuyWhenDurationBeloveMin() {
        mockVersionedService.setMinimumDuration(120);

        BuyServiceException exception = assertThrows(BuyServiceException.class, () ->
                service.buyService(new BuyServiceDTO(mockEvent.getId(), mockVersionedService.getStaticServiceId(), "03:00", "04:00")));
        assertEquals("The service's reservation must be at least 120 minutes", exception.getMessage());
    }
}

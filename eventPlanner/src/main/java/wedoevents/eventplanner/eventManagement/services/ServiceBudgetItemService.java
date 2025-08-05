package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.*;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;
import wedoevents.eventplanner.serviceManagement.repositories.VersionedServiceRepository;
import wedoevents.eventplanner.shared.Exceptions.BuyServiceException;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ServiceBudgetItemService {
    private final ServiceBudgetItemRepository serviceBudgetItemRepository;
    private final VersionedServiceRepository versionedServiceRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final EventOrganizerRepository eventOrganizerRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ServiceBudgetItemService(ServiceBudgetItemRepository serviceBudgetItemRepository,
                                    VersionedServiceRepository versionedServiceRepository,
                                    ServiceCategoryRepository serviceCategoryRepository,
                                    EventOrganizerRepository eventOrganizerRepository,
                                    EventRepository eventRepository) {
        this.serviceBudgetItemRepository = serviceBudgetItemRepository;
        this.versionedServiceRepository = versionedServiceRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.eventRepository = eventRepository;
    }

    public ServiceBudgetItemDTO getServiceBudgetItem(UUID id) {
        Optional<ServiceBudgetItem> serviceBudgetItemMaybe = serviceBudgetItemRepository.findById(id);

        if (serviceBudgetItemMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return ServiceBudgetItemDTO.toDto(serviceBudgetItemMaybe.get());
    }

    public ServiceBudgetItemDTO createServiceBudgetItem(CreateServiceBudgetItemDTO createServiceBudgetItemDTO) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(createServiceBudgetItemDTO.getServiceCategoryId()).orElseThrow(EntityNotFoundException::new);
        Event event = eventRepository.findById(createServiceBudgetItemDTO.getEventId()).orElseThrow(EntityNotFoundException::new);

        if (event.getServiceBudgetItems().stream().anyMatch(e -> e.getServiceCategory().getId().equals(createServiceBudgetItemDTO.getServiceCategoryId())))
            throw new BuyServiceException("Event already contains that category");

        ServiceBudgetItem newServiceBudgetItem = new ServiceBudgetItem();
        newServiceBudgetItem.setServiceCategory(serviceCategory);
        newServiceBudgetItem.setMaxPrice(createServiceBudgetItemDTO.getMaxPrice());

        ServiceBudgetItem createdServiceBudgetItem = serviceBudgetItemRepository.save(newServiceBudgetItem);
        event.getServiceBudgetItems().add(createdServiceBudgetItem);
        eventRepository.save(event);

        return ServiceBudgetItemDTO.toDto(createdServiceBudgetItem);
    }

    public ServiceBudgetItemDTO buyService(BuyServiceDTO buyServiceDTO) {
        VersionedService service = versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(buyServiceDTO.getServiceId()).orElseThrow(EntityNotFoundException::new);

        if (!service.getIsActive() || service.getIsPrivate() || !service.getIsAvailable())
            throw new BuyServiceException("Can't buy service that is not visible to you");

        Event event = eventRepository.findById(buyServiceDTO.getEventId()).orElseThrow(EntityNotFoundException::new);
        if (service.getAvailableEventTypes().stream().map(EventType::getId).noneMatch(id -> id.equals(event.getEventType().getId())))
            throw new BuyServiceException("Event type not in event's available event types");

        if (event.getDate().isBefore(LocalDate.now()))
            throw new BuyServiceException("Event has passed");

        LocalDateTime startTime = LocalDateTime.of(event.getDate(), LocalTime.parse(buyServiceDTO.getStartTime()));
        LocalDateTime endTime = LocalDateTime.of(event.getDate(), LocalTime.parse(buyServiceDTO.getEndTime()));
        if (serviceBudgetItemRepository.doesOverlap(buyServiceDTO.getServiceId(), startTime, endTime))
            throw new BuyServiceException("Service is unavailable at that time");

        ServiceBudgetItem sbi = event.getServiceBudgetItems().stream().filter(
                s -> s.getServiceCategory().equals(service.getStaticService().getServiceCategory()))
                .findFirst()
                .orElse(null);

        if (sbi == null) {
            UUID serviceCategoryId = service.getStaticService().getServiceCategory().getId();
            ServiceBudgetItemDTO created = createServiceBudgetItem(new CreateServiceBudgetItemDTO(buyServiceDTO.getEventId(), serviceCategoryId, 0.0));

            sbi = serviceBudgetItemRepository.findById(created.getId()).get();
        } else if (sbi.getMaxPrice() < service.getPrice() * (1 - service.getSalePercentage()))
            throw new BuyServiceException("Service too expensive");
        else if (sbi.getService() != null)
            throw new BuyServiceException("Service budget item for that event and category is already booked");

        sbi.setService(service);
        sbi.setStartTime(startTime);
        sbi.setEndTime(endTime);

        return ServiceBudgetItemDTO.toDto(serviceBudgetItemRepository.save(sbi));
    }

    public void deleteEventEmptyServiceCategoryFromBudget(UUID eventId, UUID serviceCategoryId) {
        if (!eventRepository.existsEventById(eventId))
            throw new EntityNotFoundException();

        if (!serviceCategoryRepository.existsById(serviceCategoryId)) {
            throw new EntityNotFoundException();
        }

        if (serviceBudgetItemRepository.hasBoughtServiceByEventIdAndServiceCategoryId(eventId, serviceCategoryId)) {
            throw new EntityCannotBeDeletedException();
        }

        serviceBudgetItemRepository.removeEventEmptyServiceCategory(eventId, serviceCategoryId);
    }

    public List<BookingSlotsDTO> getSlots(UUID serviceId, UUID organizerId) {
        List<Event> events = eventOrganizerRepository.getMyEventsById(organizerId);
        VersionedService service = versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(serviceId).orElseThrow(EntityNotFoundException::new);

        if (!service.getIsActive() || service.getIsPrivate() || !service.getIsAvailable())
            return new ArrayList<>();

        events = events.stream().filter(event -> {
            boolean hadPassed = !event.getDate().isAfter(LocalDate.now());
            boolean isSupported = service.getAvailableEventTypes().stream().anyMatch(type -> type.getId().equals(event.getEventType().getId()));
            if (hadPassed || !isSupported)
                return false; // if event is old or event type not in available types for this service

            List<ServiceBudgetItem> items = event.getServiceBudgetItems();
            if (items == null || items.isEmpty()) return true; // if there are no service budget items for the event

            // if there is no item for current service category or item is empty and service is cheap enough
            Optional<ServiceBudgetItem> item = items.stream().filter(i -> i.getServiceCategory().getId().equals(service.getStaticService().getServiceCategory().getId())).findFirst();
            return item.map(i -> i.getService() == null && (service.getPrice() * (1 - service.getSalePercentage())) <= i.getMaxPrice()).orElse(true);
        }).toList();

        long minDuration = service.getMinimumDuration(), maxDuration = service.getMaximumDuration();
        List<BookingSlotsDTO> slots = new ArrayList<>(); // list of possible events and their possible time tables

        for (Event event : events) {
            HashMap<String, List<String>> timeTable = new HashMap<>();  // map with start time as key and list of possible end times as value
            List<ServiceBudgetItem> bookings = serviceBudgetItemRepository.getForServiceAndDay(serviceId, event.getDate());
            LocalTime iteratorTime = LocalTime.MIN;

            for (ServiceBudgetItem booking : bookings) {
                fillPossibleTimeSlots(timeTable, iteratorTime, booking.getStartTime().toLocalTime(), minDuration, maxDuration);
                iteratorTime = booking.getEndTime().toLocalTime();
            }

            fillPossibleTimeSlots(timeTable, iteratorTime, LocalTime.MAX, minDuration, maxDuration);
            slots.add(new BookingSlotsDTO(new EventNameDTO(event), timeTable));
        }

        return slots;
    }

    private void fillPossibleTimeSlots(HashMap<String, List<String>> timeTable, LocalTime iteratorTime, LocalTime maxEndTime, long minDuration, long maxDuration) {
        long stepDuration = 15, freeSlotDuration = (long) (Math.ceil(ChronoUnit.MINUTES.between(iteratorTime, maxEndTime) / (double) stepDuration) * stepDuration);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (freeSlotDuration >= minDuration) {
            List<String> endTimes = new ArrayList<>();
            for (long duration = minDuration; duration <= maxDuration && duration <= freeSlotDuration; duration += stepDuration)
                endTimes.add(iteratorTime.plusMinutes(duration).format(formatter));

            if (!endTimes.isEmpty())
                timeTable.put(iteratorTime.format(formatter), endTimes);

            iteratorTime = iteratorTime.plusMinutes(stepDuration);
            freeSlotDuration -= stepDuration;
        }
    }

    public void changeServiceBudgetItemMaxPrice(UUID serviceBudgetItemId, Double newPrice) {
        Optional<ServiceBudgetItem> serviceBudgetItemMaybe = serviceBudgetItemRepository.findById(serviceBudgetItemId);

        if (serviceBudgetItemMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            ServiceBudgetItem pbi = serviceBudgetItemMaybe.get();

            pbi.setMaxPrice(newPrice);
            serviceBudgetItemRepository.save(pbi);
        }
    }
}
package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.CreateEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.eventManagement.dtos.ServiceBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.models.*;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;
import wedoevents.eventplanner.eventManagement.repositories.ProductBudgetItemRepository;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final ProductBudgetItemService productBudgetItemService;
    private final ServiceBudgetItemService serviceBudgetItemService;

    @Autowired
    public EventService(EventRepository eventRepository, EventTypeRepository eventTypeRepository, ProductBudgetItemService productBudgetItemService, ServiceBudgetItemService serviceBudgetItemService) {
        this.eventRepository = eventRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.productBudgetItemService = productBudgetItemService;
        this.serviceBudgetItemService = serviceBudgetItemService;
    }

    public void deleteEventEmptyProductCategoryFromBudget(UUID eventId, UUID productCategoryId) {
        if (!eventRepository.existsEventById(eventId)) {
            throw new EntityNotFoundException();
        }

        // TODO SHOULD BE TESTED

        if (eventRepository.removeEventEmptyProductCategory(eventId, productCategoryId) != 0) {
            throw new EntityCannotBeDeletedException();
        }
    }

    public void deleteEventEmptyServiceCategoryFromBudget(UUID eventId, UUID serviceCategoryId) {
        if (!eventRepository.existsEventById(eventId)) {
            throw new EntityNotFoundException();
        }

        // TODO SHOULD BE TESTED

        if (eventRepository.removeEventEmptyServiceCategory(eventId, serviceCategoryId) != 0) {
            throw new EntityCannotBeDeletedException();
        }
    }

    public EventComplexViewDTO createEvent(CreateEventDTO createEventDTO) {
        List<ProductBudgetItem> createdProductBudgetItems = createEventDTO.getProductBudgetItems()
                .stream()
                .map(productBudgetItemService::createProductBudgetItem)
                .map(pbi -> productBudgetItemService.getProductBudgetItemById(pbi.getId()).get())
                .toList();

        List<ServiceBudgetItem> createdServiceBudgetItems = createEventDTO.getServiceBudgetItems()
                .stream()
                .map(serviceBudgetItemService::createServiceBudgetItem)
                .map(sbi -> serviceBudgetItemService.getServiceBudgetItemById(sbi.getId()).get())
                .toList();

        Optional<EventType> eventTypeMaybe = eventTypeRepository.findById(createEventDTO.getEventTypeId());

        if (eventTypeMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Event newEvent = new Event();
        newEvent.setProductBudgetItems(createdProductBudgetItems);
        newEvent.setServiceBudgetItems(createdServiceBudgetItems);
//        newEvent.setEventActivities(new ArrayList<>()); // todo agenda
//        newEvent.setImages(new ArrayList<>()); // todo images with image service
        newEvent.setEventType(eventTypeMaybe.get());

        newEvent.setDescription(createEventDTO.getDescription());
        newEvent.setName(createEventDTO.getName());
        newEvent.setCity(createEventDTO.getCity());
        newEvent.setAddress(createEventDTO.getAddress());
        newEvent.setIsPublic(createEventDTO.getIsPublic());
        newEvent.setDate(createEventDTO.getDate());
        newEvent.setTime(createEventDTO.getTime());
        newEvent.setLocation(new Location(createEventDTO.getLongitude(), createEventDTO.getLatitude())); // todo map
        newEvent.setGuestCount(createEventDTO.getGuestCount());

        return EventComplexViewDTO.toDto(eventRepository.save(newEvent));
    }
}
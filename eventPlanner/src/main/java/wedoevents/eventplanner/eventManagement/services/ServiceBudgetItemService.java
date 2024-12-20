package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.*;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;
import wedoevents.eventplanner.serviceManagement.repositories.VersionedServiceRepository;
import wedoevents.eventplanner.shared.Exceptions.BuyProductException;
import wedoevents.eventplanner.shared.Exceptions.BuyServiceException;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceBudgetItemService {
    
    private final ServiceBudgetItemRepository serviceBudgetItemRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final EventRepository eventRepository;
    private final VersionedServiceRepository versionedServiceRepository;

    @Autowired
    public ServiceBudgetItemService(ServiceBudgetItemRepository serviceBudgetItemRepository, 
                                    ServiceCategoryRepository serviceCategoryRepository, 
                                    EventRepository eventRepository,
                                    VersionedServiceRepository versionedServiceRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.serviceBudgetItemRepository = serviceBudgetItemRepository;
        this.eventRepository = eventRepository;
        this.versionedServiceRepository = versionedServiceRepository;
    }

    public ServiceBudgetItemDTO createServiceBudgetItem(CreateServiceBudgetItemDTO createServiceBudgetItemDTO) {
        Optional<ServiceCategory> serviceCategoryMaybe = serviceCategoryRepository.findById(createServiceBudgetItemDTO.getServiceCategoryId());

        if (serviceCategoryMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Optional<Event> eventMaybe = eventRepository.findById(createServiceBudgetItemDTO.getEventId());

        if (eventMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Event event = eventMaybe.get();

        if (event.getServiceBudgetItems()
                .stream()
                .anyMatch(e ->
                        e.getServiceCategory().getId().equals(createServiceBudgetItemDTO.getServiceCategoryId()))) {
            throw new BuyServiceException("Event type not in event's available event types");
        }

        ServiceBudgetItem newServiceBudgetItem = new ServiceBudgetItem();

        newServiceBudgetItem.setServiceCategory(serviceCategoryMaybe.get());
        newServiceBudgetItem.setMaxPrice(createServiceBudgetItemDTO.getMaxPrice());

        ServiceBudgetItem createdServiceBudgetItem = serviceBudgetItemRepository.save(newServiceBudgetItem);

        event.getServiceBudgetItems().add(createdServiceBudgetItem);
        eventRepository.save(event);

        return ServiceBudgetItemDTO.toDto(createdServiceBudgetItem);
    }

    public ServiceBudgetItemDTO buyService(BuyServiceDTO buyServiceDTO) {
        Optional<VersionedService> versionedServiceMaybe = versionedServiceRepository.getVersionedServiceByStaticServiceIdAndLatestVersion(buyServiceDTO.getServiceId());

        if (versionedServiceMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        VersionedService versionedService = versionedServiceMaybe.get();

        if (!versionedService.getIsActive() || versionedService.getIsPrivate() || !versionedService.getIsAvailable()) {
            throw new BuyServiceException("Can't buy service that is not visible to you");
        }

        Optional<Event> eventMaybe = eventRepository.findById(buyServiceDTO.getEventId());

        if (eventMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Event event = eventMaybe.get();

        if (versionedService.getAvailableEventTypes()
                .stream()
                .map(EventType::getId)
                .noneMatch(id -> id.equals(event.getEventType().getId()))) {
            throw new BuyServiceException("Event type not in event's available event types");
        }

        // todo add logic for checking service availability here, using BuyServiceDTO additional fields
        // todo everything else is already checked (price, event type, visibility, ids existing)

        ServiceBudgetItem serviceBudgetItem;

        if (buyServiceDTO.getServiceBudgetItemId() == null) {
            ServiceBudgetItemDTO serviceBudgetItemDTO = createServiceBudgetItem(new CreateServiceBudgetItemDTO(
                    buyServiceDTO.getEventId(),
                    versionedService.getStaticService().getServiceCategory().getId(),
                    0.0
            ));

            serviceBudgetItem = serviceBudgetItemRepository.findById(serviceBudgetItemDTO.getId()).get();
        } else {
            Optional<ServiceBudgetItem> serviceBudgetItemMaybe = serviceBudgetItemRepository.findById(buyServiceDTO.getServiceBudgetItemId());

            if (serviceBudgetItemMaybe.isEmpty()) {
                throw new EntityNotFoundException();
            } else {
                serviceBudgetItem = serviceBudgetItemMaybe.get();

                if (serviceBudgetItem.getMaxPrice() < versionedService.getPrice() * (1 - versionedService.getSalePercentage())) {
                    throw new BuyServiceException("Service too expensive");
                }
            }
        }

        serviceBudgetItem.setService(versionedService);
        return ServiceBudgetItemDTO.toDto(serviceBudgetItemRepository.save(serviceBudgetItem));
    }
    
    public void deleteEventEmptyServiceCategoryFromBudget(UUID eventId, UUID serviceCategoryId) {
        if (!eventRepository.existsEventById(eventId)) {
            throw new EntityNotFoundException();
        }

        if (serviceBudgetItemRepository.removeEventEmptyServiceCategory(eventId, serviceCategoryId) != 0) {
            throw new EntityCannotBeDeletedException();
        }
    }
}
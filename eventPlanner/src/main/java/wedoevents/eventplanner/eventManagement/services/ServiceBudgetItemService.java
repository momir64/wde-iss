package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.CreateProductBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.dtos.CreateServiceBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.dtos.ProductBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.dtos.ServiceBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceBudgetItemService {

    private final ServiceBudgetItemRepository serviceBudgetItemRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ServiceBudgetItemService(ServiceBudgetItemRepository serviceBudgetItemRepository, ServiceCategoryRepository serviceCategoryRepository, EventRepository eventRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.serviceBudgetItemRepository = serviceBudgetItemRepository;
        this.eventRepository = eventRepository;
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
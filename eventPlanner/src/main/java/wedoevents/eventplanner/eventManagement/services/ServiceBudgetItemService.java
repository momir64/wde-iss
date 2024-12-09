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
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceBudgetItemService {

    private final ServiceBudgetItemRepository serviceBudgetItemRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    public ServiceBudgetItemService(ServiceBudgetItemRepository serviceBudgetItemRepository, ServiceCategoryRepository serviceCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.serviceBudgetItemRepository = serviceBudgetItemRepository;
    }

    public ServiceBudgetItemDTO createServiceBudgetItem(CreateServiceBudgetItemDTO createServiceBudgetItemDTO) {
        ServiceBudgetItem newServiceBudgetItem = new ServiceBudgetItem();

        Optional<ServiceCategory> serviceCategoryMaybe = serviceCategoryRepository.findById(createServiceBudgetItemDTO.getServiceCategoryId());

        if (serviceCategoryMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        newServiceBudgetItem.setServiceCategory(serviceCategoryMaybe.get());
        newServiceBudgetItem.setMaxPrice(createServiceBudgetItemDTO.getMaxPrice());

        return ServiceBudgetItemDTO.toDto(serviceBudgetItemRepository.save(newServiceBudgetItem));
    }

    public List<ServiceBudgetItem> getAllServiceBudgetItems() {
        return serviceBudgetItemRepository.findAll();
    }

    public Optional<ServiceBudgetItem> getServiceBudgetItemById(UUID id) {
        return serviceBudgetItemRepository.findById(id);
    }

    public void deleteServiceBudgetItem(UUID id) {
        serviceBudgetItemRepository.deleteById(id);
    }
}
package wedoevents.eventplanner.eventManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceBudgetItemService {

    private final ServiceBudgetItemRepository serviceBudgetItemRepository;

    @Autowired
    public ServiceBudgetItemService(ServiceBudgetItemRepository serviceBudgetItemRepository) {
        this.serviceBudgetItemRepository = serviceBudgetItemRepository;
    }

    public ServiceBudgetItem saveServiceBudgetItem(ServiceBudgetItem serviceBudgetItem) {
        return serviceBudgetItemRepository.save(serviceBudgetItem);
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
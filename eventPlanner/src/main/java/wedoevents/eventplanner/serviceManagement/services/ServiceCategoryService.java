package wedoevents.eventplanner.serviceManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceCategoryService {

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    public List<ServiceCategory> getAllServiceCategories() {
        return serviceCategoryRepository.findAll();
    }

    public ServiceCategory saveServiceCategory(ServiceCategory category) {
        return serviceCategoryRepository.save(category);
    }

    public ServiceCategory getServiceCategoryById(UUID id) {
        return serviceCategoryRepository.findById(id).orElse(null);
    }

    public void deleteServiceCategory(UUID id) {
        serviceCategoryRepository.deleteById(id);
    }
}

package wedoevents.eventplanner.serviceManagement.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.serviceManagement.models.ServiceEntity;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public ServiceEntity saveService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public ServiceEntity getServiceById(UUID id) {
        return serviceRepository.findById(id).orElse(null);
    }

    public void deleteService(UUID id) {
        serviceRepository.deleteById(id);
    }
}
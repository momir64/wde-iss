package wedoevents.eventplanner.serviceManagement.services;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.services.EventTypeService;
import wedoevents.eventplanner.serviceManagement.dtos.CreateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.UpdateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.VersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.repositories.StaticServiceRepository;
import wedoevents.eventplanner.serviceManagement.repositories.VersionedServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceService {

    @Autowired
    private VersionedServiceRepository versionedServiceRepository;

    @Autowired
    private StaticServiceRepository staticServiceRepository;

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EventTypeService eventTypeService;

    private VersionedService incrementServiceVersionAndSave(VersionedService versionedService) {
        entityManager.detach(versionedService);
        versionedService.incrementVersion();

        return versionedServiceRepository.save(versionedService);
    }

    public List<VersionedServiceDTO> getAllServicesWithLatestVersions() {
        return versionedServiceRepository.getAllVersionedServicesWithMaxVersions().stream().map(
                VersionedServiceDTO::toDto
        ).toList();
    }

    public VersionedServiceDTO createService(CreateVersionedServiceDTO createVersionedServiceDTO) {
        // todo backend check if the static service id exists
        VersionedService newVersionedService;
        ServiceCategory matchingServiceCategory = serviceCategoryService.getServiceCategoryById(
                createVersionedServiceDTO.getServiceCategoryId()
        );

        StaticService newMatchingStaticService = new StaticService();
        newMatchingStaticService.setServiceCategory(matchingServiceCategory);
        staticServiceRepository.save(newMatchingStaticService);

        newVersionedService = new VersionedService();
        newVersionedService.setStaticService(newMatchingStaticService);
        newVersionedService.setStaticServiceId(newMatchingStaticService.getStaticServiceId());
        newVersionedService.setVersion(1);

        newVersionedService.setName(createVersionedServiceDTO.getName());
        newVersionedService.setSalePercentage(createVersionedServiceDTO.getSalePercentage());
        newVersionedService.setImages(createVersionedServiceDTO.getImages());
        newVersionedService.setDescription(createVersionedServiceDTO.getDescription());
        newVersionedService.setIsPrivate(createVersionedServiceDTO.getIsPrivate());
        newVersionedService.setIsAvailable(createVersionedServiceDTO.getIsAvailable());
        newVersionedService.setDuration(createVersionedServiceDTO.getDuration());
        newVersionedService.setCancellationDeadline(createVersionedServiceDTO.getCancellationDeadline());
        newVersionedService.setReservationDeadline(createVersionedServiceDTO.getReservationDeadline());
        newVersionedService.setIsActive(createVersionedServiceDTO.getIsActive());
        newVersionedService.setIsConfirmationManual(createVersionedServiceDTO.getIsConfirmationManual());
        newVersionedService.setPrice(createVersionedServiceDTO.getPrice());

        List<EventType> eventTypes = new ArrayList<>();
        for (UUID eventTypeId : createVersionedServiceDTO.getAvailableEventTypeIds()) {
            eventTypes.add(eventTypeService.getEventTypeById(eventTypeId));
        }
        newVersionedService.setAvailableEventTypes(eventTypes);

        // todo: do backend checks on UUIDs of event types
        return VersionedServiceDTO.toDto(versionedServiceRepository.save(newVersionedService));
    }

    public VersionedServiceDTO updateVersionedService(UpdateVersionedServiceDTO updateVersionedServiceDTO) {
        // todo backend check if the static service id exists
        VersionedService newVersionedService = versionedServiceRepository.
                getVersionedServiceByStaticServiceIdAndLatestVersion(updateVersionedServiceDTO.getStaticServiceId());

        newVersionedService.setName(updateVersionedServiceDTO.getName());
        newVersionedService.setSalePercentage(updateVersionedServiceDTO.getSalePercentage());
        newVersionedService.setImages(updateVersionedServiceDTO.getImages());
        newVersionedService.setDescription(updateVersionedServiceDTO.getDescription());
        newVersionedService.setIsPrivate(updateVersionedServiceDTO.getIsPrivate());
        newVersionedService.setIsAvailable(updateVersionedServiceDTO.getIsAvailable());
        newVersionedService.setDuration(updateVersionedServiceDTO.getDuration());
        newVersionedService.setCancellationDeadline(updateVersionedServiceDTO.getCancellationDeadline());
        newVersionedService.setReservationDeadline(updateVersionedServiceDTO.getReservationDeadline());
        newVersionedService.setIsActive(updateVersionedServiceDTO.getIsActive());
        newVersionedService.setIsConfirmationManual(updateVersionedServiceDTO.getIsConfirmationManual());
        newVersionedService.setPrice(updateVersionedServiceDTO.getPrice());

        List<EventType> eventTypes = new ArrayList<>();
        for (UUID eventTypeId : updateVersionedServiceDTO.getAvailableEventTypeIds()) {
            eventTypes.add(eventTypeService.getEventTypeById(eventTypeId));
        }
        newVersionedService.setAvailableEventTypes(eventTypes);

        // todo: do backend checks on UUIDs of event types
        return VersionedServiceDTO.toDto(incrementServiceVersionAndSave(newVersionedService));
    }

    public VersionedServiceDTO getVersionedServiceById(UUID staticServiceId) {
        // todo: null check
        return VersionedServiceDTO.toDto(versionedServiceRepository.getVersionedServiceByStaticServiceIdAndLatestVersion(staticServiceId));
    }

    public VersionedServiceDTO getVersionedServiceByStaticServiceIdAndVersion(Integer version, UUID staticServiceId) {
        // todo: null check
        return VersionedServiceDTO.toDto(versionedServiceRepository.getVersionedServiceByStaticServiceIdAndVersion(staticServiceId, version));
    }

    public void deactivateService(UUID staticServiceId) {
        // todo backend check if the static service id exists
        VersionedService newVersionedService = versionedServiceRepository.
                getVersionedServiceByStaticServiceIdAndLatestVersion(staticServiceId);

        newVersionedService.setIsAvailable(false);

        incrementServiceVersionAndSave(newVersionedService);
    }
}
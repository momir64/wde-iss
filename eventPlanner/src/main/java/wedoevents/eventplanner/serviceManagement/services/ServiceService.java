package wedoevents.eventplanner.serviceManagement.services;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.services.EventTypeService;
import wedoevents.eventplanner.serviceManagement.dtos.UpdateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.CreateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.VersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.VersionedServiceForSellerDTO;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.models.VersionedServiceImage;
import wedoevents.eventplanner.serviceManagement.repositories.StaticServiceRepository;
import wedoevents.eventplanner.serviceManagement.repositories.VersionedServiceRepository;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private ImageService imageService;

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

    public VersionedServiceDTO createService(CreateVersionedServiceDTO createVersionedServiceDTO, MultipartFile[] images) throws IOException {
        // todo backend check if the static service category id exists
        VersionedService newVersionedService;
        ServiceCategory matchingServiceCategory = serviceCategoryService.getServiceCategoryById(
                createVersionedServiceDTO.getServiceCategoryId()
        );

        StaticService newMatchingStaticService = new StaticService();
        newMatchingStaticService.setServiceCategory(matchingServiceCategory);
        newMatchingStaticService = staticServiceRepository.save(newMatchingStaticService);

        List<String> imageNames = new ArrayList<>();
        for (MultipartFile imageFile : images) {
            imageNames.add(imageService.saveImageToStorage(imageFile, new ImageLocationConfiguration(
                            "service",
                            newMatchingStaticService.getStaticServiceId(),
                            1
                    )
            ));
        }

        newVersionedService = new VersionedService();
        newVersionedService.setStaticService(newMatchingStaticService);
        newVersionedService.setStaticServiceId(newMatchingStaticService.getStaticServiceId());
        newVersionedService.setVersion(1);
        newVersionedService.setIsLastVersion(true);

        newVersionedService.setName(createVersionedServiceDTO.getName());
        newVersionedService.setSalePercentage(createVersionedServiceDTO.getSalePercentage());
        newVersionedService.setDescription(createVersionedServiceDTO.getDescription());
        newVersionedService.setIsPrivate(createVersionedServiceDTO.getIsPrivate());
        newVersionedService.setIsAvailable(createVersionedServiceDTO.getIsAvailable());
        newVersionedService.setMaximumDuration(createVersionedServiceDTO.getMaximumDuration());
        newVersionedService.setMinimumDuration(createVersionedServiceDTO.getMinimumDuration());
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

        newVersionedService = versionedServiceRepository.save(newVersionedService);
        newVersionedService.setImages(imageNames);

        // todo: do backend checks on UUIDs of event types
        return VersionedServiceDTO.toDto(versionedServiceRepository.save(newVersionedService));
    }

    public VersionedServiceDTO updateVersionedService(UpdateVersionedServiceDTO updateVersionedServiceDTO, MultipartFile[] images) throws IOException {
        Optional<VersionedService> versionedServiceMaybe = versionedServiceRepository
                .getVersionedServiceByStaticServiceIdAndLatestVersion(updateVersionedServiceDTO.getStaticServiceId());

        // todo prevent editing deleted services

        if (versionedServiceMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        VersionedService oldVersionOfVersionedService = versionedServiceMaybe.get();

        oldVersionOfVersionedService.setIsLastVersion(false);

        oldVersionOfVersionedService = versionedServiceRepository.save(oldVersionOfVersionedService);

        VersionedService newVersionedService = new VersionedService(oldVersionOfVersionedService);

        List<String> imageNames = new ArrayList<>();
        for (MultipartFile imageFile : images) {
            imageNames.add(imageService.saveImageToStorage(imageFile, new ImageLocationConfiguration(
                            "service",
                            updateVersionedServiceDTO.getStaticServiceId(),
                    oldVersionOfVersionedService.getVersion() + 1
                    )
            ));
        }

        newVersionedService.setIsLastVersion(true);
        newVersionedService.setName(updateVersionedServiceDTO.getName());
        newVersionedService.setSalePercentage(updateVersionedServiceDTO.getSalePercentage());
        newVersionedService.setDescription(updateVersionedServiceDTO.getDescription());
        newVersionedService.setIsPrivate(updateVersionedServiceDTO.getIsPrivate());
        newVersionedService.setIsAvailable(updateVersionedServiceDTO.getIsAvailable());
        newVersionedService.setMaximumDuration(updateVersionedServiceDTO.getMaximumDuration());
        newVersionedService.setMinimumDuration(updateVersionedServiceDTO.getMinimumDuration());
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

        newVersionedService = incrementServiceVersionAndSave(newVersionedService);
        newVersionedService.setImages(imageNames);

        // todo: do backend checks on UUIDs of event types
        return VersionedServiceDTO.toDto(versionedServiceRepository.save(newVersionedService));
    }

    public VersionedServiceDTO getVersionedServiceById(UUID staticServiceId) {
        Optional<VersionedService> versionedServiceMaybe = versionedServiceRepository.getVersionedServiceByStaticServiceIdAndLatestVersion(staticServiceId);

        // todo prevent getting deleted services and prevent regular users from getting private services

        if (versionedServiceMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return VersionedServiceDTO.toDto(versionedServiceMaybe.get());
    }

    public VersionedServiceForSellerDTO getVersionedServiceEditableById(UUID staticServiceId) throws IOException {
        Optional<VersionedService> versionedServiceMaybe = versionedServiceRepository.getVersionedServiceByStaticServiceIdAndLatestVersion(staticServiceId);

        if (versionedServiceMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return VersionedServiceForSellerDTO.toDto(versionedServiceMaybe.get(), imageService);
    }

    public VersionedServiceDTO getVersionedServiceByStaticServiceIdAndVersion(Integer version, UUID staticServiceId) {
        // todo: null check
        return VersionedServiceDTO.toDto(versionedServiceRepository.getVersionedServiceByStaticServiceIdAndVersion(staticServiceId, version));
    }

    public void deactivateService(UUID staticServiceId) {
        // todo pull all versions to deleted, so that no one can access the latest non deleted version
        // todo do the same for private
        // todo potentially add to static service
        Optional<VersionedService> versionedServiceMaybe = versionedServiceRepository.getVersionedServiceByStaticServiceIdAndLatestVersion(staticServiceId);

        if (versionedServiceMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        VersionedService newVersionedService = versionedServiceMaybe.get();

        newVersionedService.setIsAvailable(false);

        incrementServiceVersionAndSave(newVersionedService);
    }
}
package wedoevents.eventplanner.serviceManagement.services;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.services.EventTypeService;
import wedoevents.eventplanner.serviceManagement.dtos.*;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;
import wedoevents.eventplanner.serviceManagement.repositories.StaticServiceRepository;
import wedoevents.eventplanner.serviceManagement.repositories.VersionedServiceRepository;
import wedoevents.eventplanner.shared.Exceptions.UpdatePriceException;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.repositories.userTypes.SellerRepository;

import java.io.IOException;
import java.util.*;

@Service
public class ServiceService {

    @Autowired
    private VersionedServiceRepository versionedServiceRepository;

    @Autowired
    private StaticServiceRepository staticServiceRepository;

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SellerRepository sellerRepository;

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

    public List<CatalogueServiceDTO> getAllServicesLatestVersionsFromSellerForCatalogue(UUID sellerId) {
        return versionedServiceRepository.getAllVersionedServicesWithMaxVersionsFromSeller(sellerId).stream().map(
                CatalogueServiceDTO::toDto
        ).toList();
    }

    public VersionedServiceDTO createService(CreateVersionedServiceDTO createVersionedServiceDTO, MultipartFile[] images) throws IOException {
        // todo check for fields that must be non-null

        Optional<Seller> sellerMaybe = sellerRepository.findById(createVersionedServiceDTO.getSellerId());

        if (sellerMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Seller seller = sellerMaybe.get();

        ServiceCategory matchingServiceCategory;
        if (createVersionedServiceDTO.getSuggestedCategory() == null) {
            Optional<ServiceCategory> serviceCategoryMaybe = serviceCategoryRepository.findById(
                    createVersionedServiceDTO.getServiceCategoryId()
            );

            if (serviceCategoryMaybe.isEmpty()) {
                throw new EntityNotFoundException();
            }

            matchingServiceCategory = serviceCategoryMaybe.get();
        } else {
            matchingServiceCategory = new ServiceCategory();
            matchingServiceCategory.setName(createVersionedServiceDTO.getSuggestedCategory());
            matchingServiceCategory.setDescription(createVersionedServiceDTO.getSuggestedCategoryDescription());
            matchingServiceCategory.setIsPending(true);
            matchingServiceCategory.setIsDeleted(false);

            matchingServiceCategory = serviceCategoryRepository.save(matchingServiceCategory);
        }

        StaticService newMatchingStaticService = new StaticService();

        newMatchingStaticService.setServiceCategory(matchingServiceCategory);
        newMatchingStaticService.setPending(matchingServiceCategory.getIsPending());
        newMatchingStaticService = staticServiceRepository.save(newMatchingStaticService);

        seller.getMyServices().add(newMatchingStaticService);
        sellerRepository.save(seller);

        VersionedService newVersionedService = new VersionedService();
        newVersionedService.setStaticService(newMatchingStaticService);
        newVersionedService.setStaticServiceId(newMatchingStaticService.getStaticServiceId());
        newVersionedService.setVersion(1);
        newVersionedService.setIsLastVersion(true);

        newVersionedService.setName(createVersionedServiceDTO.getName());
        newVersionedService.setSalePercentage(createVersionedServiceDTO.getSalePercentage());
        newVersionedService.setDescription(createVersionedServiceDTO.getDescription());
        newVersionedService.setIsPrivate(createVersionedServiceDTO.getIsPrivate());
        newVersionedService.setIsAvailable(createVersionedServiceDTO.getIsAvailable());
        newVersionedService.setIsActive(true);
        newVersionedService.setMaximumDuration(createVersionedServiceDTO.getMaximumDuration());
        newVersionedService.setMinimumDuration(createVersionedServiceDTO.getMinimumDuration());
        newVersionedService.setCancellationDeadline(createVersionedServiceDTO.getCancellationDeadline());
        newVersionedService.setReservationDeadline(createVersionedServiceDTO.getReservationDeadline());
        newVersionedService.setIsConfirmationManual(createVersionedServiceDTO.getIsConfirmationManual());
        newVersionedService.setPrice(createVersionedServiceDTO.getPrice());

        List<EventType> eventTypes = new ArrayList<>();
        for (UUID eventTypeId : createVersionedServiceDTO.getAvailableEventTypeIds()) {
            eventTypes.add(eventTypeService.getEventTypeById(eventTypeId));
        }
        newVersionedService.setAvailableEventTypes(eventTypes);

        newVersionedService = versionedServiceRepository.save(newVersionedService);

        List<String> imageNames = new ArrayList<>();
        for (MultipartFile imageFile : images) {
            imageNames.add(imageService.saveImageToStorage(imageFile, new ImageLocationConfiguration(
                            "service",
                            newMatchingStaticService.getStaticServiceId(),
                            1
                    )
            ));
        }

        newVersionedService.setImages(imageNames);

        // todo: do backend checks on UUIDs of event types
        return VersionedServiceDTO.toDto(versionedServiceRepository.save(newVersionedService));
    }

    public VersionedServiceDTO updateVersionedService(UpdateVersionedServiceDTO updateVersionedServiceDTO, MultipartFile[] images) throws IOException {
        // todo check for fields that must be non-null

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

        newVersionedService.setIsLastVersion(true);
        newVersionedService.setName(updateVersionedServiceDTO.getName());
        newVersionedService.setSalePercentage(updateVersionedServiceDTO.getSalePercentage());
        newVersionedService.setDescription(updateVersionedServiceDTO.getDescription());
        newVersionedService.setIsPrivate(updateVersionedServiceDTO.getIsPrivate());
        newVersionedService.setIsAvailable(updateVersionedServiceDTO.getIsAvailable());
        newVersionedService.setIsActive(true);
        newVersionedService.setMaximumDuration(updateVersionedServiceDTO.getMaximumDuration());
        newVersionedService.setMinimumDuration(updateVersionedServiceDTO.getMinimumDuration());
        newVersionedService.setCancellationDeadline(updateVersionedServiceDTO.getCancellationDeadline());
        newVersionedService.setReservationDeadline(updateVersionedServiceDTO.getReservationDeadline());
        newVersionedService.setIsConfirmationManual(updateVersionedServiceDTO.getIsConfirmationManual());
        newVersionedService.setPrice(updateVersionedServiceDTO.getPrice());

        List<EventType> eventTypes = new ArrayList<>();
        for (UUID eventTypeId : updateVersionedServiceDTO.getAvailableEventTypeIds()) {
            eventTypes.add(eventTypeService.getEventTypeById(eventTypeId));
        }
        newVersionedService.setAvailableEventTypes(eventTypes);

        newVersionedService = incrementServiceVersionAndSave(newVersionedService);

        List<String> imageNames = new ArrayList<>();
        for (MultipartFile imageFile : images) {
            imageNames.add(imageService.saveImageToStorage(imageFile, new ImageLocationConfiguration(
                            "service",
                            updateVersionedServiceDTO.getStaticServiceId(),
                            oldVersionOfVersionedService.getVersion() + 1
                    )
            ));
        }
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

        // todo prevent getting deleted services

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

    public void updateCataloguePrices(UUID sellerId, ToBeUpdatedServicesCatalogueDTO toBeUpdatedServicesCatalogueDTO) {
        Collection<VersionedService> servicesFromSeller = this.versionedServiceRepository.getAllVersionedServicesWithMaxVersionsFromSeller(sellerId);

        for (CatalogueServiceDTO newPrice : toBeUpdatedServicesCatalogueDTO.getToBeUpdated()) {
            Optional<VersionedService> matchingVersionedServiceMaybe =
                    servicesFromSeller
                            .stream()
                            .filter(s -> s.getStaticServiceId().equals(newPrice.getServiceId()))
                            .findFirst();

            if (matchingVersionedServiceMaybe.isEmpty()) {
                // todo unauthorized exception
                throw new EntityNotFoundException();
            }

            if (newPrice.getPrice() == null) {
                throw new UpdatePriceException("Price must be set");
            }

            VersionedService matchingVersionedService = matchingVersionedServiceMaybe.get();

            if (!matchingVersionedService.getPrice().equals(newPrice.getPrice()) ||
                    !matchingVersionedService.getSalePercentage().equals(newPrice.getSalePercentage())) {
                matchingVersionedService.setPrice(newPrice.getPrice());
                matchingVersionedService.setSalePercentage(newPrice.getSalePercentage());

                // TODO BIG: ACTUAL UPDATE USING updateVersionedService FUNCTION
                // TODO BIG: FIX updateVersionedService FUNCTION TO ACCEPT EVERY FIELD AS OPTIONAL
                versionedServiceRepository.save(matchingVersionedService);
            }
        }
    }
}
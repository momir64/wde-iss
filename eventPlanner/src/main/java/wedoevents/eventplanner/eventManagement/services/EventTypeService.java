package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.EventTypeDTO;
import wedoevents.eventplanner.eventManagement.dtos.ExtendedEventTypeDTO;
import wedoevents.eventplanner.eventManagement.dtos.RecommendedCategoriesDTO;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public EventType saveEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    public EventType getEventTypeById(UUID id) {
        return eventTypeRepository.findById(id).orElse(null);
    }

    public void deleteEventType(UUID id) {
        eventTypeRepository.deleteById(id);
    }

    public List<ExtendedEventTypeDTO> getAllEventTypes() {
        List<EventType> eventTypes = eventTypeRepository.findAll();
        return eventTypes.stream()
                .map(this::mapToResponseDTO)
                .sorted(Comparator.comparing(ExtendedEventTypeDTO::getName))
                .collect(Collectors.toList());
    }

    public ExtendedEventTypeDTO mapToResponseDTO(EventType eventType) {
        ExtendedEventTypeDTO dto = new ExtendedEventTypeDTO();
        dto.setId(eventType.getId());
        dto.setName(eventType.getName());
        dto.setDescription(eventType.getDescription());
        dto.setIsActive(eventType.getIsActive());

        List<ListingCategoryDTO> listingCategories = new ArrayList<>();

        if (eventType.getRecommendedProductCategories() != null) {
            listingCategories.addAll(
                    eventType.getRecommendedProductCategories().stream()
                            .map(ListingCategoryDTO::fromProductCategory)
                            .toList()
            );
        }

        if (eventType.getRecommendedServiceCategories() != null) {
            listingCategories.addAll(
                    eventType.getRecommendedServiceCategories().stream()
                            .map(ListingCategoryDTO::fromServiceCategory)
                            .toList()
            );
        }

        dto.setListingCategories(listingCategories); // Set the combined list
        return dto;
    }

    public ExtendedEventTypeDTO updateEventType(UUID id, ExtendedEventTypeDTO updatedEventTypeDTO) {
        EventType existingEventType = getEventTypeById(id);

        if (existingEventType == null) {
            throw new EntityNotFoundException("EventType not found with id: " + id);
        }

        existingEventType.setName(updatedEventTypeDTO.getName());
        existingEventType.setDescription(updatedEventTypeDTO.getDescription());
        existingEventType.setIsActive(updatedEventTypeDTO.getIsActive());

        if (updatedEventTypeDTO.getListingCategories() != null) {
            existingEventType.setRecommendedProductCategories(
                    updatedEventTypeDTO.getListingCategories().stream()
                            .filter(c -> c.getListingType() == ListingType.PRODUCT)
                            .map(c -> new ProductCategory(c.getId(), c.getName())) // Simplified mapping
                            .collect(Collectors.toList())
            );

            existingEventType.setRecommendedServiceCategories(
                    updatedEventTypeDTO.getListingCategories().stream()
                            .filter(c -> c.getListingType() == ListingType.SERVICE)
                            .map(c -> new ServiceCategory(c.getId(), c.getName())) // Simplified mapping
                            .collect(Collectors.toList())
            );
        }

        EventType savedEventType = saveEventType(existingEventType);

        return mapToResponseDTO(savedEventType);
    }
    public EventType mapToEntity(ExtendedEventTypeDTO eventTypeDTO) {
        EventType eventType = new EventType();
        eventType.setName(eventTypeDTO.getName());
        eventType.setDescription(eventTypeDTO.getDescription());
        eventType.setIsActive(eventTypeDTO.getIsActive());

        if (eventTypeDTO.getListingCategories() != null) {
            eventType.setRecommendedProductCategories(
                    eventTypeDTO.getListingCategories().stream()
                            .filter(c -> c.getListingType() == ListingType.PRODUCT)
                            .map(c -> new ProductCategory(c.getId(), c.getName())) // Simplified mapping
                            .collect(Collectors.toList())
            );

            eventType.setRecommendedServiceCategories(
                    eventTypeDTO.getListingCategories().stream()
                            .filter(c -> c.getListingType() == ListingType.SERVICE)
                            .map(c -> new ServiceCategory(c.getId(), c.getName())) // Simplified mapping
                            .collect(Collectors.toList())
            );
        }

        return eventType;
    }

    public RecommendedCategoriesDTO getRecommendedCategoriesForType(UUID eventTypeId) {
        List<ListingCategoryDTO> recommendedServiceCategories =
                eventTypeRepository
                        .getRecommendedServiceCategoriesByEventTypeId(eventTypeId)
                        .stream()
                        .map(ListingCategoryDTO::fromServiceCategory)
                        .toList();
        List<ListingCategoryDTO> recommendedProductCategories =
                eventTypeRepository
                        .getRecommendedProductCategoriesByEventTypeId(eventTypeId)
                        .stream()
                        .map(ListingCategoryDTO::fromProductCategory)
                        .toList();

        return new RecommendedCategoriesDTO(recommendedServiceCategories, recommendedProductCategories);
    }
}


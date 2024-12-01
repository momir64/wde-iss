package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.EventTypeResponseDTO;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.dtos.ProductCategoryDTO;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.serviceManagement.dtos.ServiceCategoryDTO;
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

    public List<EventTypeResponseDTO> getAllEventTypes() {
        List<EventType> eventTypes = eventTypeRepository.findAll();
        return eventTypes.stream()
                .map(this::mapToResponseDTO)
                .sorted(Comparator.comparing(EventTypeResponseDTO::getName))
                .collect(Collectors.toList());
    }

    public EventTypeResponseDTO mapToResponseDTO(EventType eventType) {
        EventTypeResponseDTO dto = new EventTypeResponseDTO();
        dto.setId(eventType.getId());
        dto.setName(eventType.getName());
        dto.setDescription(eventType.getDescription());
        dto.setIsActive(eventType.getIsActive());

        List<ListingCategoryDTO> listingCategories = new ArrayList<>();

        // Add product categories to the combined list
        if (eventType.getRecommendedProductCategories() != null) {
            listingCategories.addAll(
                    eventType.getRecommendedProductCategories().stream()
                            .map(this::mapToListingCategoryDTO)
                            .collect(Collectors.toList())
            );
        }

        // Add service categories to the combined list
        if (eventType.getRecommendedServiceCategories() != null) {
            listingCategories.addAll(
                    eventType.getRecommendedServiceCategories().stream()
                            .map(this::mapToListingCategoryDTO)
                            .collect(Collectors.toList())
            );
        }

        dto.setListingCategories(listingCategories); // Set the combined list
        return dto;
    }

    private ListingCategoryDTO mapToListingCategoryDTO(Object category) {
        ListingCategoryDTO dto = new ListingCategoryDTO();
        if (category instanceof ProductCategory) {
            ProductCategory productCategory = (ProductCategory) category;
            dto.setId(productCategory.getId());
            dto.setName(productCategory.getName());
            dto.setIsPending(productCategory.getIsPending());
            dto.setDescription(productCategory.getDescription());
            dto.setIsDeleted(productCategory.getIsDeleted());
            dto.setListingType(ListingType.PRODUCT); // Add appropriate type
        } else if (category instanceof ServiceCategory) {
            ServiceCategory serviceCategory = (ServiceCategory) category;
            dto.setId(serviceCategory.getId());
            dto.setName(serviceCategory.getName());
            dto.setDescription(serviceCategory.getDescription());
            dto.setIsDeleted(serviceCategory.getIsDeleted());
            dto.setListingType(ListingType.SERVICE); // Add appropriate type
        }
        return dto;
    }
    public EventTypeResponseDTO updateEventType(UUID id, EventTypeResponseDTO updatedEventTypeDTO) {
        EventType existingEventType = getEventTypeById(id);

        if (existingEventType == null) {
            throw new EntityNotFoundException("EventType not found with id: " + id);
        }

        // Map DTO fields to the existing entity
        existingEventType.setName(updatedEventTypeDTO.getName());
        existingEventType.setDescription(updatedEventTypeDTO.getDescription());
        existingEventType.setIsActive(updatedEventTypeDTO.getIsActive());

        // Map recommended categories if applicable
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

        // Save updated entity
        EventType savedEventType = saveEventType(existingEventType);

        // Map entity back to DTO for response
        return mapToResponseDTO(savedEventType);
    }
    public EventType mapToEntity(EventTypeResponseDTO eventTypeDTO) {
        EventType eventType = new EventType();
        eventType.setName(eventTypeDTO.getName());
        eventType.setDescription(eventTypeDTO.getDescription());
        eventType.setIsActive(eventTypeDTO.getIsActive());

        // Map recommended categories if applicable
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
}


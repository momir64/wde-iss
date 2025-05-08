package wedoevents.eventplanner.shared.services.mappers;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.eventManagement.dtos.CalendarEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventActivityDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventActivity;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.listingManagement.dtos.ListingDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.serviceManagement.models.StaticService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarEventMapper {
    public static CalendarEventDTO toCalendarEventDTO(Event event) {
        CalendarEventDTO dto = new CalendarEventDTO();
        dto.setId(event.getId().toString());
        dto.setName(event.getName());
        dto.setDate(event.getDate());
        dto.setTime(event.getTime());
        dto.setLocation(event.getAddress());

        dto.setActivites(event.getEventActivities().stream()
                .map(CalendarEventMapper::toEventActivityDTO)
                .collect(Collectors.toList()));

        List<ListingDTO> listings = new ArrayList<>();
        if(!event.getServiceBudgetItems().isEmpty()){
            listings.addAll(event.getNonEmptyServiceBudgetItems().stream()
                    .map(CalendarEventMapper::toListingDTO)
                    .toList());
        }
        if(!event.getProductBudgetItems().isEmpty()){
            listings.addAll(event.getNonEmptyProductBudgetItems().stream()
                    .map(CalendarEventMapper::toListingDTO)
                    .toList());
        }
        dto.setListings(listings);
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();
        dto.setImages(event.getImages().stream().map(image -> String.format("%s/api/v1/events/%s/images/%s", baseUrl, event.getId(), image)).collect(Collectors.toList()));


        return dto;
    }

    private static EventActivityDTO toEventActivityDTO(EventActivity eventActivity) {
        EventActivityDTO dto = new EventActivityDTO();
        dto.setId(eventActivity.getId());
        dto.setName(eventActivity.getName());
        dto.setDescription(eventActivity.getDescription());
        dto.setStartTime(eventActivity.getStartTime());
        dto.setEndTime(eventActivity.getEndTime());
        dto.setLocation(eventActivity.getLocation());
        return dto;
    }

    private static ListingDTO toListingDTO(ServiceBudgetItem serviceItem) {
        ListingDTO dto = new ListingDTO();
        if(serviceItem.getService() != null){
            StaticService service = serviceItem.getService().getStaticService();
            dto.setId(service.getStaticServiceId());
        }
        dto.setType(ListingType.SERVICE);
        dto.setName(serviceItem.getService().getName());
        dto.setDescription(serviceItem.getService().getDescription());
        dto.setPrice(serviceItem.getService().getPrice());
        return dto;
    }

    private static ListingDTO toListingDTO(ProductBudgetItem productItem) {
        ListingDTO dto = new ListingDTO();
        StaticProduct product = productItem.getProduct().getStaticProduct();
        dto.setId(product.getStaticProductId());
        dto.setType(ListingType.PRODUCT);
        dto.setName(productItem.getProduct().getName());
        dto.setDescription(productItem.getProduct().getDescription());
        dto.setPrice(productItem.getProduct().getPrice());
        return dto;
    }

}

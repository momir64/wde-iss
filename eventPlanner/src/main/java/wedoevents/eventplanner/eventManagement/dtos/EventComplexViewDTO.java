package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.eventManagement.models.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class EventComplexViewDTO {
    private UUID id;
    private String name;
    private String description;
    private LocalDate date;
    private String time;
    private String city;
    private String address;
    private Integer guestCount;
    private Boolean isPublic;
    private List<String> images;
    private UUID eventTypeId;
    private List<ProductBudgetItemDTO> productBudgetItems;
    private List<ServiceBudgetItemDTO> serviceBudgetItems;
    private Double longitude;
    private Double latitude;
    private List<UUID> eventActivityIds;
    private Double rating;
    public EventComplexViewDTO(Event event) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();
        id = event.getId();
        name = event.getName();
        description = event.getDescription();
        date = event.getDate();
        time = event.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        city = event.getCity().getName();
        address = event.getAddress();
        guestCount = event.getGuestCount();
        isPublic = event.getIsPublic();
        images = event.getImages().stream().map(image -> String.format("%s/api/v1/events/%s/images/%s", baseUrl, id, image)).collect(Collectors.toList());
        eventTypeId = event.getEventType().getId();
        productBudgetItems = event.getProductBudgetItems().stream().map(ProductBudgetItemDTO::toDto).toList();
        serviceBudgetItems = event.getServiceBudgetItems().stream().map(ServiceBudgetItemDTO::toDto).toList();
        longitude = event.getLocation().getLongitude();
        latitude = event.getLocation().getLatitude();
        eventActivityIds = new ArrayList<>();  // todo agenda > event.getEventActivities().stream().map(EventActivity::getId).toList()
        rating = 0.0;
    }
    public EventComplexViewDTO(Event event, Double rating){
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();
        id = event.getId();
        name = event.getName();
        description = event.getDescription();
        date = event.getDate();
        time = event.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        city = event.getCity().getName();
        address = event.getAddress();
        guestCount = event.getGuestCount();
        isPublic = event.getIsPublic();
        images = event.getImages().stream().map(image -> String.format("%s/api/v1/events/%s/images/%s", baseUrl, id, image)).collect(Collectors.toList());
        eventTypeId = event.getEventType().getId();
        productBudgetItems = event.getProductBudgetItems().stream().map(ProductBudgetItemDTO::toDto).toList();
        serviceBudgetItems = event.getServiceBudgetItems().stream().map(ServiceBudgetItemDTO::toDto).toList();
        longitude = event.getLocation().getLongitude();
        latitude = event.getLocation().getLatitude();
        eventActivityIds = new ArrayList<>();  // todo agenda > event.getEventActivities().stream().map(EventActivity::getId).toList()
        this.rating = rating;
    }
}

package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EventComplexViewDTO {
    private UUID          id;
    private String        name;
    private String        description;
    private LocalDate     date;
    private LocalTime     time;
    private String        city;
    private String        address;
    private Integer       guestCount;
    private Boolean       isPublic;
    private List<String>  images;
    private UUID          eventTypeId;
    private List<ProductBudgetItemDTO>    productBudgetItems;
    private List<ServiceBudgetItemDTO>    serviceBudgetItems;
    private Double        longitude;
    private Double        latitude;
    private List<UUID>    eventActivityIds;
    private Double        rating;

    public static EventComplexViewDTO toDto(Event event) {
        return new EventComplexViewDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getTime(),
                event.getCity().getName(),
                event.getAddress(),
                event.getGuestCount(),
                event.getIsPublic(),
                event.getImages(),
                event.getEventType().getId(),
                event.getProductBudgetItems().stream().map(ProductBudgetItemDTO::toDto).toList(),
                event.getServiceBudgetItems().stream().map(ServiceBudgetItemDTO::toDto).toList(),
                null, // todo map event.getLocation().getLongitude()
                null, // todo map event.getLocation().getLatitude()
                new ArrayList<>(), // todo agenda > event.getEventActivities().stream().map(EventActivity::getId).toList()
                0.0 // todo izračunati prosečnu ocenu iz reviewova
        );
    }
}

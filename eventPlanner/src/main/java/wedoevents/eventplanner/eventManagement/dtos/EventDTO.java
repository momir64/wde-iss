package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EventDTO {
    private UUID          id;
    private String        city;
    private String        name;
    private String        description;
    private LocalDateTime date;
    private Double        rating;
    private int           guestCount;
    private List<String>  images;
    private UUID          eventTypeId;
    private List<UUID>    productBudgetItemIds;
    private List<UUID>    serviceBudgetItemIds;
    private UUID          locationId;
    private List<UUID>    eventActivityIds;
}

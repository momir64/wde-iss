package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.userManagement.dtos.EvenReviewResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EventDetailedViewDTO {
    private UUID id;
    private String city;
    private String address;
    private Boolean isPublic;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private Integer guestCount;
    private EventType eventType;
    private Double longitude;
    private Double latitude;
    private Double averageRating;
    private List<EvenReviewResponseDTO> reviews;
    private boolean isDeletable;
    private boolean isFavorite;
    private boolean isAccepted;
}

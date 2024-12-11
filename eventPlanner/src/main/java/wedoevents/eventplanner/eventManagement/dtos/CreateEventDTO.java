package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CreateEventDTO {
    private String name;
    private String description;
    private String city;
    private String address;
    private Boolean isPublic;
    private LocalDate date;
    private LocalTime time;
    private Integer guestCount;
    private Double latitude;
    private Double longitude;

    private UUID eventTypeId;
    private List<UUID> eventActivitiyIds; // todo agenda

    private UUID organizerId;

    // private List<MultipartFile> images; // todo add images with image service
}

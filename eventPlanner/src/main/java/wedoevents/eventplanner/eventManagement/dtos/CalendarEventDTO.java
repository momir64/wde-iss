package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.dtos.ListingDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
public class CalendarEventDTO {
    private String id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private Double rating;
    private String location;
    private List<EventActivityDTO> activities;
    private List<ListingDTO> listings;
}

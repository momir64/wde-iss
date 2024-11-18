package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ServiceEntityDTO {
    private UUID id;

    private String name;
    private Boolean sale;
    private List<String> images;
    private String description;
    private Boolean isPrivate;
    private Boolean isAvailable;
    private int duration;
    private LocalDate cancellationDeadline;
    private LocalDate reservationDeadline;
    private Boolean isActive;
    private Boolean isConfirmationManual;
    private double price;
    private int version;
    private LocalTime openingTime;
    private LocalTime closingTime;

    private UUID serviceCategoryId;
    private List<UUID> availableEventTypeIds;
}

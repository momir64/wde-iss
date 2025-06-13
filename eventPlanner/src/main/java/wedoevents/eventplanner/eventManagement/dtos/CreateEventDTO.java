package wedoevents.eventplanner.eventManagement.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CreateEventDTO {

    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Address is required")
    private String address;
    @NotNull(message = "isPublic must be provided")
    private Boolean isPublic;
    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Date must be today or in the future")
    private LocalDate date;
    @NotNull(message = "Time is required")
    private LocalTime time;
    @Min(value = 1, message = "There must be at least one guest")
    private Integer guestCount;
    private Double latitude;
    private Double longitude;

    @NotNull(message = "Event type must be selected")
    private UUID eventTypeId;
    @NotNull(message = "Agenda list is required")
    private List<UUID> agenda;

    @NotNull(message = "Organizer profile ID is required")
    private UUID organizerProfileId;
    private UUID eventId;
}

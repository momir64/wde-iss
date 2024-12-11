package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GuestListDTO {
    private List<String> emails;
    private UUID eventId;
    private String organizerName;
    private String organizerSurname;
}

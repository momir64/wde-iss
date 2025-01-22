package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserReportResponseDTO {
    private UUID id;
    private String reason;
    private LocalDateTime reportDateTime;
    private String profileToCredentials;
    private String profileFromCredentials;
}

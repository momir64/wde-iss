package wedoevents.eventplanner.notificationManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDTO {
    private UUID id;

    private boolean isSeen;
    private LocalDate date;
    private String message;
    private String title;
    private String webRedirect;
    private String mobileRedirect;

    // one of these three will be -1 or null

    private UUID serviceCategoryId;
    private UUID productCategoryId;
    private UUID eventId;
}

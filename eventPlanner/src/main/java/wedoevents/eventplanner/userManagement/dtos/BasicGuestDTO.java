package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.AllArgsConstructor;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;

@Getter
@AllArgsConstructor
public class BasicGuestDTO {
    private String name;
    private String surname;
    private String email;

    public static BasicGuestDTO from(Guest guest) {
        return new BasicGuestDTO(guest.getName(), guest.getSurname(), guest.getProfile().getEmail());
    }
}

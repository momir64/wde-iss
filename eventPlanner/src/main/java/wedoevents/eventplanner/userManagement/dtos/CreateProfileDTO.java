package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.shared.models.City;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.UserType;
import wedoevents.eventplanner.userManagement.models.userTypes.*;

@Setter
@Getter
public class CreateProfileDTO {
    private String email;
    private String password;
    private boolean isActive;
    private boolean areNotificationsMuted;
    private UserType userType;
    private String name;
    private String surname;
    private String description;
    private String city;
    private String address;
    private String telephoneNumber;

    public String createRoleName(){
        return switch (userType) {
            case ADMIN -> "ROLE_ADMIN";
            case SELLER -> "ROLE_SELLER";
            case EVENTORGANIZER -> "ROLE_ORGANIZER";
            default -> "ROLE_GUEST";
        };
    }

    public Object createUserEntity(Profile profile) {
        switch (userType) {
            case ADMIN:
                Admin admin = new Admin();
                admin.setProfile(profile);
                return admin;
            case EVENTORGANIZER:
                EventOrganizer eventOrganizer = new EventOrganizer();
                eventOrganizer.setName(this.name);
                eventOrganizer.setSurname(this.surname);
                eventOrganizer.setCity(new City(this.city));
                eventOrganizer.setAddress(this.address);
                eventOrganizer.setTelephoneNumber(this.telephoneNumber);
                eventOrganizer.setProfile(profile);
                return eventOrganizer;
            case GUEST:
                Guest guest = new Guest();
                guest.setProfile(profile);
                return guest;
            case SELLER:
                Seller seller = new Seller();
                seller.setName(this.name);
                seller.setSurname(this.surname);
                seller.setDescription(this.description);
                seller.setCity(new City(this.city));
                seller.setAddress(this.address);
                seller.setTelephoneNumber(this.telephoneNumber);
                seller.setProfile(profile);
                return seller;
            default:
                return null;
        }
    }
}

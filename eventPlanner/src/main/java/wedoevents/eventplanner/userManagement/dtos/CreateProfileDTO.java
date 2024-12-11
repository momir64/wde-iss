package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
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
    private String city;
    private String address;
    private String postalNumber;
    private String telephoneNumber;
    private String image;

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
                eventOrganizer.setCity(this.city);
                eventOrganizer.setAddress(this.address);
                eventOrganizer.setPostalNumber(this.postalNumber);
                eventOrganizer.setTelephoneNumber(this.telephoneNumber);
                eventOrganizer.setProfileImage(image);
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
                seller.setCity(this.city);
                seller.setAddress(this.address);
                seller.setPostalNumber(this.postalNumber);
                seller.setTelephoneNumber(this.telephoneNumber);
                seller.setProfileImage(this.image);
                seller.setProfile(profile);
                return seller;
            default:
                return null;
        }
    }
}

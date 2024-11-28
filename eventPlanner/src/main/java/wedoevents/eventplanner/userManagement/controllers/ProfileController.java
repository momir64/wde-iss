package wedoevents.eventplanner.userManagement.controllers;

import org.springdoc.core.service.GenericResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.shared.services.emailService.IEmailService;
import wedoevents.eventplanner.userManagement.models.CreateProfileDTO;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.RegistrationAttempt;
import wedoevents.eventplanner.userManagement.models.userTypes.Admin;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.services.ProfileService;
import wedoevents.eventplanner.userManagement.services.RegistrationAttemptService;
import wedoevents.eventplanner.userManagement.services.UserService;
import wedoevents.eventplanner.userManagement.services.userTypes.AdminService;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;
import wedoevents.eventplanner.userManagement.services.userTypes.SellerService;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final String email = "uvoduvod1@gmail.com";
    private final ProfileService profileService;
    private final EventOrganizerService eventOrganizerService;
    private final GuestService guestService;
    private final SellerService sellerService;
    private final RegistrationAttemptService registrationAttemptService;
    private final UserService userService;
    private final IEmailService emailService;
    private final GenericResponseService responseBuilder;

    @Autowired
    public ProfileController(ProfileService profileService, EventOrganizerService eventOrganizerService, SellerService sellerService, GuestService guestService,
                             RegistrationAttemptService registrationAttemptService, UserService userService, @Qualifier("sendGridEmailService") IEmailService emailService, GenericResponseService responseBuilder) {
        this.profileService = profileService;
        this.eventOrganizerService = eventOrganizerService;
        this.sellerService = sellerService;
        this.guestService = guestService;
        this.registrationAttemptService = registrationAttemptService;
        this.userService = userService;
        this.emailService = emailService;
        this.responseBuilder = responseBuilder;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createProfile(@RequestBody CreateProfileDTO createProfileDTO) {

        //check if there is already a verified profile with the same email
        Optional<Profile> existingProfile = profileService.findProfileByEmail(createProfileDTO.getEmail());
        if (existingProfile.isPresent() && existingProfile.get().isVerified()) {
            return ResponseEntity.badRequest().body("Already verified");
        }

        // add/update the profile
        Profile profile = new Profile();
        profile.BuildProfile(createProfileDTO.getEmail(),createProfileDTO.getPassword(),createProfileDTO.isActive(),createProfileDTO.isAreNotificationsMuted(),false);
        profile = profileService.createOrUpdateProfile(profile);

        //delete all users that reference the same profile (edge case)
        userService.deleteAllUsersByProfile(profile);

        // create/update a user based on the userType

        Object userEntity = createProfileDTO.createUserEntity(profile);
        if (userEntity instanceof EventOrganizer) {
            eventOrganizerService.createOrUpdateEventOrganizer((EventOrganizer) userEntity);
        } else if (userEntity instanceof Guest) {
            guestService.createOrUpdateGuest((Guest) userEntity);
        } else if (userEntity instanceof Seller) {
            sellerService.createOrUpdateSeller((Seller) userEntity);
        } else {
            return ResponseEntity.badRequest().body(null); // Unknown user type
        }

        // call registrationAttemptService to create another attempt
        RegistrationAttempt registrationAttempt = new RegistrationAttempt();
        registrationAttempt.setProfile(profile);
        registrationAttempt.setTime(LocalDateTime.now());
        registrationAttemptService.saveRegistrationAttempt(registrationAttempt);




        // send email for verification
        //for development purposes send to the same email all of the time
        try{
            String response = emailService.sendVerificationEmail(email,createProfileDTO.getName(),createProfileDTO.getSurname(),
                    registrationAttempt.getId().toString(),profile.getId().toString());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sendgrid se usrao u gace fr on god no cap");
        }

        //return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable UUID id) {
        Optional<Profile> profile = profileService.findProfileById(id);
        return profile.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Profile> getProfileByEmail(@PathVariable String email) {
        Optional<Profile> profile = profileService.findProfileByEmail(email);
        return profile.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<Profile> verifyProfile(@PathVariable UUID id) {
        Profile verifiedProfile = profileService.verifyProfile(id);
        return ResponseEntity.ok(verifiedProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
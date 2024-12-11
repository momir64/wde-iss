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
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.services.ProfileService;
import wedoevents.eventplanner.userManagement.services.RegistrationAttemptService;
import wedoevents.eventplanner.userManagement.services.UserService;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;
import wedoevents.eventplanner.userManagement.services.userTypes.SellerService;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import wedoevents.eventplanner.userManagement.dtos.ExtendedProfileDTO;
import wedoevents.eventplanner.userManagement.models.UserType;


@RestController
@RequestMapping("/api/v1/profiles")
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


    @GetMapping("/{id}")
    public ResponseEntity<?> viewProfile(@PathVariable("id") UUID profileId) {
        try {
            // call get profile service

            ExtendedProfileDTO profile = buildDummyProfile();
            return ResponseEntity.ok(profile);
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to view this profile");
//        } catch (ProfileNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping
    public ResponseEntity<?> editProfile(@RequestBody ExtendedProfileDTO profileDto) {
        try {
            // call update profile service

            return ResponseEntity.ok("Profile updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid profile data");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to edit this profile");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateAccount(@PathVariable("id") UUID profileId) {
        try {
            // call deactivate account service

            return ResponseEntity.ok("Account deactivated successfully");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request to deactivate account");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to deactivate this account");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
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
        profile.BuildProfile(createProfileDTO.getEmail(), createProfileDTO.getPassword(), true, createProfileDTO.isAreNotificationsMuted(), false);
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
        //for development purposes send to the same email all the time
        try {
            String response = emailService.sendVerificationEmail(email, createProfileDTO.getEmail(), createProfileDTO.getName(), createProfileDTO.getSurname(),
                                                                 registrationAttempt.getId().toString(), profile.getId().toString());
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sendgrid error");
        }
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

    public ExtendedProfileDTO buildDummyProfile() {
        ExtendedProfileDTO profile = new ExtendedProfileDTO();

        profile.setProfileId(UUID.randomUUID());
        profile.setUserId(UUID.randomUUID());
        profile.setEmail("example@example.com");
        profile.setPassword("securePassword123");
        profile.setActive(true);
        profile.setAreNotificationsMuted(false);
        profile.setVerified(true);
        profile.setName("John");
        profile.setSurname("Doe");
        profile.setCity("New York");
        profile.setAddress("123 Main St");
        profile.setPostalNumber("10001");
        profile.setTelephoneNumber("+1-555-1234");
        profile.setProfileImage("profile_image_url.jpg");
        profile.setUserType(UserType.GUEST);

        return profile;
    }

}
package wedoevents.eventplanner.userManagement.controllers;
import io.jsonwebtoken.Jwt;
import jakarta.persistence.EntityNotFoundException;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wedoevents.eventplanner.shared.config.auth.JwtUtil;
import wedoevents.eventplanner.shared.services.emailService.IEmailService;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;
import wedoevents.eventplanner.userManagement.dtos.CreateProfileDTO;
import wedoevents.eventplanner.userManagement.dtos.UpdateProfileDTO;
import wedoevents.eventplanner.userManagement.dtos.UserBlockDTO;
import wedoevents.eventplanner.userManagement.dtos.UserBlockResponseDTO;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.RegistrationAttempt;
import wedoevents.eventplanner.userManagement.models.Role;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.services.ProfileService;
import wedoevents.eventplanner.userManagement.services.RegistrationAttemptService;
import wedoevents.eventplanner.userManagement.services.RoleService;
import wedoevents.eventplanner.userManagement.services.UserService;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;
import wedoevents.eventplanner.userManagement.services.userTypes.SellerService;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
    private final ImageService imageService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;

    @Autowired
    public ProfileController(ProfileService profileService, EventOrganizerService eventOrganizerService, SellerService sellerService, GuestService guestService, ImageService imageService, RoleService roleService,
                             RegistrationAttemptService registrationAttemptService,JwtUtil jwtUtil, UserService userService, @Qualifier("sendGridEmailService") IEmailService emailService, GenericResponseService responseBuilder) {
        this.profileService = profileService;
        this.eventOrganizerService = eventOrganizerService;
        this.sellerService = sellerService;
        this.guestService = guestService;
        this.registrationAttemptService = registrationAttemptService;
        this.userService = userService;
        this.emailService = emailService;
        this.responseBuilder = responseBuilder;
        this.imageService = imageService;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> viewProfile(@PathVariable("id") UUID profileId) {
        return profileService.getExtendedProfileById(profileId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping(value = "/images/{id}/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getProfileImage(@PathVariable("id") UUID id, @PathVariable("image_name") String imageName) {
        try {
            ImageLocationConfiguration config = new ImageLocationConfiguration("profile", id);
            Optional<byte[]> image = imageService.getImage(imageName, config);
            if (image.isEmpty())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
            return ResponseEntity.ok().body(image.get());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping("/images")
    public ResponseEntity<?> putProfileImage(@RequestParam("imageFile") MultipartFile imageFile,
                                             @RequestParam("profileId") UUID profileId) {
        try {
            Optional<Profile> profile = profileService.findProfileById(profileId);
            if(profile.isEmpty()){
                return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("Profile not found");
            }
            ImageLocationConfiguration config = new ImageLocationConfiguration("profile", profile.get().getId());

            String imageName = imageService.saveImageToStorage(imageFile, config);

            profile.get().setImageName(imageName);
            profileService.createProfile(profile.get());

            return ResponseEntity.ok(imageName);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not save image");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable("id") UUID profileId,
                                           @RequestBody UpdateProfileDTO updateProfileDTO) {
        return profileService.updateProfile(profileId, updateProfileDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateAccount(@PathVariable("id") UUID profileId) {
        boolean isDeactivated = profileService.deactivateProfile(profileId);

        if (!isDeactivated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();
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
        Optional<Role> role = roleService.getRole(createProfileDTO.createRoleName());
        if(role.isEmpty()){
            return ResponseEntity.badRequest().body(null); // Unknown role
        }
        profile.BuildProfile(createProfileDTO.getEmail(),createProfileDTO.getPassword(),true,createProfileDTO.isAreNotificationsMuted(),false, role.get());


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
        try{
            String response = emailService.sendVerificationEmail(email,createProfileDTO.getEmail(),createProfileDTO.getName(),createProfileDTO.getSurname(),
                    registrationAttempt.getId().toString(),profile.getId().toString());
            return ResponseEntity.ok(profile.getId());
        }catch (Exception e){
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


    @GetMapping("/{profileId}/blocking/to")
    public ResponseEntity<?> getBlockedUsers(@PathVariable UUID profileId) {
        Optional<Profile> profile = profileService.findProfileById(profileId);
        if(profile.isEmpty()){
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("Profile not found");
        }
        UserBlockResponseDTO userBlockResponseDTO = new UserBlockResponseDTO();
        userBlockResponseDTO.setBlockedUserIds(profileService.getBlockedUserIds(profile.get()));
        return ResponseEntity.ok(userBlockResponseDTO);
    }

    @GetMapping("/{profileId}/blocking/from")
    public ResponseEntity<?> getBlockingUsers(@PathVariable UUID profileId) {
        Optional<Profile> profile = profileService.findProfileById(profileId);
        if(profile.isEmpty()){
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("Profile not found");
        }
        UserBlockResponseDTO userBlockResponseDTO = new UserBlockResponseDTO();
        userBlockResponseDTO.setBlockedUserIds(profileService.getBlockingUserIds(profileId));
        return ResponseEntity.ok(userBlockResponseDTO);
    }


    @PutMapping("/blocking")
    public ResponseEntity<?> blockUser(@RequestBody UserBlockDTO userBlockDTO) {

        if(userBlockDTO.getFromId() == userBlockDTO.getToId()){
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("Cannot block yourself");
        }


        Optional<Profile> profileFrom = profileService.findProfileById(userBlockDTO.getFromId());
        if(profileFrom.isEmpty()){
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("Profile not found");
        }

        Optional<Profile> profileTo = profileService.findProfileById(userBlockDTO.getToId());
        if(profileTo.isEmpty()){
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("Profile not found");
        }

        profileService.toggleBlockUser(profileFrom.get(), profileTo.get());


        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/images", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getProfileImageV2(@PathVariable("id") UUID id) {
        try {
            Profile profile = profileService.findProfileById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
            ImageLocationConfiguration config = new ImageLocationConfiguration("profile", id);
            Optional<byte[]> image = imageService.getImage(profile.getImageName(), config);
            if (image.isEmpty())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
            return ResponseEntity.ok().body(image.get());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

}
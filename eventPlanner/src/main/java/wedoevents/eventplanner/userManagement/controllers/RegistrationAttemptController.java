package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.RegistrationAttempt;
import wedoevents.eventplanner.userManagement.models.RegistrationAttemptDTO;
import wedoevents.eventplanner.userManagement.services.ProfileService;
import wedoevents.eventplanner.userManagement.services.RegistrationAttemptService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/registrationAttempts")
public class RegistrationAttemptController {

    private final RegistrationAttemptService registrationAttemptService;

    private final ProfileService profileService;

    @Autowired
    public RegistrationAttemptController(RegistrationAttemptService registrationAttemptService,ProfileService profileService) {
        this.registrationAttemptService = registrationAttemptService;
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<RegistrationAttempt> createRegistrationAttempt(@RequestBody RegistrationAttempt registrationAttempt) {
        RegistrationAttempt savedAttempt = registrationAttemptService.saveRegistrationAttempt(registrationAttempt);
        return ResponseEntity.ok(savedAttempt);
    }


    @GetMapping("/verify/{id}/{profileId}")
    public ResponseEntity<?> verifyRegistration(@PathVariable UUID id, @PathVariable UUID profileId) {

        String errorUrl = "http://localhost:4200/error/";

        // Step 1: Get the profile associated with the profileId from the RegistrationAttemptDTO
        Optional<Profile> profileOptional = profileService.findProfileById(profileId);

        // Check if the profile exists
        if (profileOptional.isEmpty()) {
            String message = "Profile%20not%20found";
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).header("Location", errorUrl+message).build();
        }

        Profile profile = profileOptional.get();



        // Step 2: Check if the profile is already verified
        if (profile.isVerified()) {
            String message = "Profile%20is%20already%20verified";
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).header("Location", errorUrl+message).build();
        }



        // Step 3: Get the most recent registration attempt for the profile
        Optional<RegistrationAttempt> mostRecentAttempt = registrationAttemptService.getMostRecentRegistrationAttemptByProfileId(profileId);

        if (mostRecentAttempt.isEmpty()) {
            String message = "No%20registration%20attempts%20found%20for%20this%20profile";
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).header("Location", errorUrl+message).build();
        }

        // Step 4: Check if the most recent registration attempt matches the one in the request
        RegistrationAttempt recentAttempt = mostRecentAttempt.get();

        if (!recentAttempt.getId().equals(id)) {
            String message = "The%20registration%20attempt%20does%20not%20match%20the%20most%20recent%20one";
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).header("Location", errorUrl+message).build();
        }

        // Step 5: Make sure the verification is not late
        if (Duration.between(recentAttempt.getTime(), LocalDateTime.now()).toHours() > 48) {
            String message = "Verification%20too%20late";
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).header("Location", errorUrl+message).build();
        }

        // Step 6: Verify the profile (set the profile as verified)
        profile.setVerified(true);
        profileService.createProfile(profile);

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:4200/login").build();
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("Profile successfully verified.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<RegistrationAttempt> getRegistrationAttemptById(@PathVariable UUID id) {
        return registrationAttemptService.getRegistrationAttemptById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RegistrationAttempt>> getAllRegistrationAttempts() {
        return ResponseEntity.ok(registrationAttemptService.getAllRegistrationAttempts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistrationAttempt(@PathVariable UUID id) {
        registrationAttemptService.deleteRegistrationAttempt(id);
        return ResponseEntity.noContent().build();
    }
}
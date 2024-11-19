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


    @PutMapping("/verify/{id}/{profileId}")
    public ResponseEntity<?> verifyRegistration(@PathVariable Long id, @PathVariable UUID profileId) {

        // Step 1: Get the profile associated with the profileId from the RegistrationAttemptDTO
        Profile profile = profileService.findProfileById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        // Step 2: Check if the profile is already verified
        if (profile.isVerified()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Profile is already verified.");
        }

        // Step 3: Get the most recent registration attempt for the profile
        Optional<RegistrationAttempt> mostRecentAttempt = registrationAttemptService.getMostRecentRegistrationAttemptByProfileId(profileId);

        if (mostRecentAttempt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No registration attempts found for this profile.");
        }

        // Step 4: Check if the most recent registration attempt matches the one in the request
        RegistrationAttempt recentAttempt = mostRecentAttempt.get();

        if (!recentAttempt.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The registration attempt does not match the most recent one.");
        }

        // Step 5: Make sure the verification is not late
        if (Duration.between(recentAttempt.getTime(), LocalDateTime.now()).toHours() > 24) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification too late");
        }

        // Step 6: Verify the profile (set the profile as verified)
        profile.setVerified(true);
        profileService.createProfile(profile);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Profile successfully verified.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<RegistrationAttempt> getRegistrationAttemptById(@PathVariable Long id) {
        return registrationAttemptService.getRegistrationAttemptById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RegistrationAttempt>> getAllRegistrationAttempts() {
        return ResponseEntity.ok(registrationAttemptService.getAllRegistrationAttempts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistrationAttempt(@PathVariable Long id) {
        registrationAttemptService.deleteRegistrationAttempt(id);
        return ResponseEntity.noContent().build();
    }
}
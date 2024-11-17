package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.models.CreateProfileDTO;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.services.ProfileService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody CreateProfileDTO createProfileDTO) {

        //check if there is already a verified profile with the same email
        Optional<Profile> existingProfile = profileService.findProfileByEmail(createProfileDTO.getEmail());
        if (existingProfile.isPresent() && existingProfile.get().isVerified()) {
            return ResponseEntity.badRequest().body(null);
        }

        // add/update the profile
        Profile profile = new Profile();
        profile.BuildProfile(createProfileDTO.getEmail(),createProfileDTO.getPassword(),createProfileDTO.isActive(),createProfileDTO.isAreNotificationsMuted(),false);
        profile = profileService.createProfile(profile);

        // create a user based on the userType


        // call registrationAttemptService to create another attempt
        Profile createdProfile = profileService.createProfile(profile);

        // send email for verification

        return ResponseEntity.ok(createdProfile);
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
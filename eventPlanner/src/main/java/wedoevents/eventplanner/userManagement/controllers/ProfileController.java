package wedoevents.eventplanner.userManagement.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.dtos.ExtendedProfileDTO;
import wedoevents.eventplanner.userManagement.models.UserType;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

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
        } catch (Exception e){
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
        } catch (Exception e){
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
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
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
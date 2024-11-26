package wedoevents.eventplanner.userManagement.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.dtos.ExtendedProfileDTO;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @GetMapping("/{id}")
    public ResponseEntity<?> viewProfile(@PathVariable("id") UUID profileId) {
        try {
            // call get profile service

            ExtendedProfileDTO profile = new ExtendedProfileDTO(); // Placeholder for actual profile data
            return ResponseEntity.ok(profile);
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to view this profile");
//        } catch (ProfileNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body("Exception");
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
            return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body("Exception");
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
            return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body("Exception");
        }
    }
}
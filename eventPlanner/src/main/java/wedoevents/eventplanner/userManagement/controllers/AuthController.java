package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.dtos.ExtendedProfileDTO;
import wedoevents.eventplanner.userManagement.dtos.LoginDTO;
import wedoevents.eventplanner.userManagement.dtos.RegistrationDTO;
import wedoevents.eventplanner.userManagement.dtos.UserAccessDTO;
import wedoevents.eventplanner.userManagement.models.ChatMessage;
import wedoevents.eventplanner.userManagement.models.UserType;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @PostMapping("/register")
    public ResponseEntity<?> createChatMessage(@RequestBody RegistrationDTO request) {
        try {
            //call service for registration

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
//        } catch (ConflictException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyRegistration(@RequestBody String registrationAttemptId) {
        try {
            // call verification service
            return ResponseEntity.ok("Verification successful");
//        } catch (NotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration attempt not found");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized verification attempt");
//        } catch (ConflictException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Verification already processed");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        try {
            // call login user service

            UserAccessDTO access = new UserAccessDTO();
            access.setJwt("afafafsafasfa");
            access.setProfileId(UUID.randomUUID());
            access.setUserType(UserType.SELLER);
            return ResponseEntity.ok(access);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid login data");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody UserAccessDTO request) {
        try {
            // call logout service

            return ResponseEntity.ok("Logout successful");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }
}

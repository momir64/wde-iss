package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.shared.config.auth.JwtUtil;
import wedoevents.eventplanner.userManagement.dtos.*;
import wedoevents.eventplanner.userManagement.models.ChatMessage;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.UserType;
import wedoevents.eventplanner.userManagement.services.ProfileService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final ProfileService profileService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, ProfileService profileService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.profileService = profileService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            Optional<Profile> profile = profileService.findProfileByEmail(request.getEmail());
            String role = "";
            if(profile.isPresent()) {
                role = profile.get().getRole().getRoleName();
            }
            String token = jwtUtil.generateToken(request.getEmail(), List.of(role));
            return ResponseEntity.ok(new TokenDTO(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new TokenDTO(e.getMessage()));
        }
    }



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

package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.shared.config.auth.JwtUtil;
import wedoevents.eventplanner.userManagement.dtos.*;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.services.ProfileService;
import wedoevents.eventplanner.userManagement.services.UserReportService;
import wedoevents.eventplanner.userManagement.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final ProfileService profileService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserReportService userReportService;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, ProfileService profileService, PasswordEncoder passwordEncoder, UserService userService, UserReportService userReportService) {
        this.authenticationManager = authenticationManager;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userReportService = userReportService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        try {
            Optional<Profile> profile = profileService.findProfileByEmail(request.getEmail());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if (profile.isEmpty())
                throw new UsernameNotFoundException("User with the email " + request.getEmail() + " not found!");
            UUID profileId = profile.get().getId();

            LocalDateTime banEndTime = userReportService.getBanEndDateIfBanned(profileId);
            if (banEndTime != null) {
                String message = "User is banned until " + banEndTime;
                return ResponseEntity.status(401).body(message);
            }


            UUID userId = userService.getUserId(profileId);
            String role = profile.get().getRole().getRoleName();
            String token = JwtUtil.generateToken(request.getEmail(), List.of(role), profile.get().getId(), userId);
            return ResponseEntity.ok(new TokenDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new TokenDTO(e.getMessage()));
        }
    }
}

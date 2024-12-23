package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.shared.config.auth.JwtUtil;
import wedoevents.eventplanner.userManagement.dtos.*;
import wedoevents.eventplanner.userManagement.models.ChatMessage;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.UserType;
import wedoevents.eventplanner.userManagement.services.ProfileService;
import wedoevents.eventplanner.userManagement.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final ProfileService profileService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, ProfileService profileService, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO request) {
        try {
            Optional<Profile> profile = profileService.findProfileByEmail(request.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword() )
            );
            String role = "";
            UUID profileId = UUID.randomUUID();
            if(profile.isPresent()) {
                role = profile.get().getRole().getRoleName();
                profileId = profile.get().getId();
            }
            UUID userId = userService.getUserId(profileId);
            String token = jwtUtil.generateToken(request.getEmail(), List.of(role),profile.get().getId(),userId);
            return ResponseEntity.ok(new TokenDTO(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new TokenDTO(e.getMessage()));
        }
    }
}

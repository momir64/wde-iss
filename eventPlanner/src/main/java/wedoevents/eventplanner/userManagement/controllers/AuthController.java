package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, ProfileService profileService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO request) {
        try {
            Optional<Profile> profile = profileService.findProfileByEmail(request.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword() )
            );
            String role = "";
            if(profile.isPresent()) {
                role = profile.get().getRole().getRoleName();
            }
            String token = jwtUtil.generateToken(request.getEmail(), List.of(role),profile.get().getId());
            return ResponseEntity.ok(new TokenDTO(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new TokenDTO(e.getMessage()));
        }
    }
}

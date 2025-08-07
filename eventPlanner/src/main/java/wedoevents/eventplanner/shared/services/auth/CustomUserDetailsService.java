package wedoevents.eventplanner.shared.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.repositories.ProfileRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ProfileRepository profileRepository;

    @Autowired
    public CustomUserDetailsService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Profile> p = profileRepository.findByEmail(email);
        var x = profileRepository.findAll();
        if (p.isPresent()) {
            Profile profile = p.get();
            return new org.springframework.security.core.userdetails.User(profile.getEmail(), profile.getPassword(), profile.getAuthorities());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}

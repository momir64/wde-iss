package wedoevents.eventplanner.shared.services.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.repositories.ProfileRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ProfileRepository profileRepository;

    public CustomUserDetailsService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Profile> p = profileRepository.findByEmail(email);
        if(p.isPresent()) {
            return p.get();
        }else{
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}

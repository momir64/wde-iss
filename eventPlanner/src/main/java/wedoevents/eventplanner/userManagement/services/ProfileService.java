package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Optional<Profile> findProfileById(UUID id) {
        return profileRepository.findById(id);
    }

    public Optional<Profile> findProfileByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile verifyProfile(UUID profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        profile.setVerified(true);
        return profileRepository.save(profile);
    }

    public void deleteProfile(UUID profileId) {
        profileRepository.deleteById(profileId);
    }
}
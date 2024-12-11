package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.repositories.ProfileRepository;

import java.util.ArrayList;
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

    public Profile createOrUpdateProfile(Profile profile) {
        Optional<Profile> existingProfile = profileRepository.findByEmail(profile.getEmail());
        if (existingProfile.isPresent()) {
            Profile profileToUpdate = existingProfile.get();
            // Copy all fields from the input profile to the existing profile
            BeanUtils.copyProperties(profile, profileToUpdate, "id", "email"); // "id" and "email" are excluded to avoid changing primary key fields
            return profileRepository.save(profileToUpdate);
        } else {
            // Create a new profile
            return profileRepository.save(profile);
        }
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

    public Profile createEmptyProfile(String email) {
        Profile profile = new Profile();
        profile.BuildProfile(email, "Password123!", true, false, false);

        profile.setBlockedUsers(new ArrayList<>());

        return profileRepository.save(profile);
    }


}
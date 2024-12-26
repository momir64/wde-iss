package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.shared.models.City;
import wedoevents.eventplanner.userManagement.dtos.ExtendedProfileDTO;
import wedoevents.eventplanner.userManagement.dtos.UpdateProfileDTO;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.Role;
import wedoevents.eventplanner.userManagement.models.UserType;
import wedoevents.eventplanner.userManagement.repositories.ProfileRepository;
import wedoevents.eventplanner.userManagement.repositories.RoleRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.AdminRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.GuestRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.SellerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final SellerRepository sellerRepository;
    private final GuestRepository guestRepository;
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final EventOrganizerRepository eventOrganizerRepository;
    private PasswordEncoder passwordEncoder;



    @Autowired
    public ProfileService(ProfileRepository profileRepository, EventOrganizerRepository eventOrganizerRepository, SellerRepository sellerRepository, GuestRepository guestRepository, AdminRepository adminRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.sellerRepository = sellerRepository;
        this.guestRepository = guestRepository;
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Profile createProfile(Profile profile) {
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return profileRepository.save(profile);
    }

    public Profile createOrUpdateProfile(Profile profile) {
        Optional<Profile> existingProfile = profileRepository.findByEmail(profile.getEmail());
        if (existingProfile.isPresent()) {
            Profile profileToUpdate = existingProfile.get();
            // Copy all fields from the input profile to the existing profile
            BeanUtils.copyProperties(profile, profileToUpdate, "id", "email"); // "id" and "email" are excluded to avoid changing primary key fields
            profileToUpdate.setPassword(passwordEncoder.encode(profile.getPassword()));
            return profileRepository.save(profileToUpdate);
        } else {
            // Create a new profile
            profile.setPassword(passwordEncoder.encode(profile.getPassword()));
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

    public boolean deactivateProfile(UUID profileId) {
        Optional<Profile> profileOpt = profileRepository.findById(profileId);

        if (profileOpt.isEmpty()) {
            return false;
        }

        Profile profile = profileOpt.get();
        profile.setActive(false);
        profileRepository.save(profile);
        return true;
    }

    public Optional<ExtendedProfileDTO> getExtendedProfileById(UUID profileId) {
        Optional<Profile> profileOpt = profileRepository.findById(profileId);

        if (profileOpt.isEmpty()) {
            return Optional.empty();
        }

        Profile profile = profileOpt.get();
        ExtendedProfileDTO dto = new ExtendedProfileDTO();
        dto.setProfileId(profile.getId());
        dto.setEmail(profile.getEmail());
        dto.setAreNotificationsMuted(profile.isAreNotificationsMuted());
        dto.setPassword(profile.getPassword()); // need hashing later on

        sellerRepository.findByProfile(profile).ifPresent(seller -> {
            dto.setName(seller.getName());
            dto.setSurname(seller.getSurname());
            dto.setCity(seller.getCity().getName());
            dto.setAddress(seller.getAddress());
            dto.setTelephoneNumber(seller.getTelephoneNumber());
            dto.setDescription(seller.getDescription());
            dto.setUserType(UserType.SELLER);
        });

        if (dto.getUserType() == null) {
            eventOrganizerRepository.findByProfile(profile).ifPresent(organizer -> {
                dto.setName(organizer.getName());
                dto.setSurname(organizer.getSurname());
                dto.setCity(organizer.getCity().getName());
                dto.setAddress(organizer.getAddress());
                dto.setTelephoneNumber(organizer.getTelephoneNumber());
                dto.setUserType(UserType.EVENTORGANIZER);
            });
        }

        return Optional.of(dto);
    }



    public Optional<ExtendedProfileDTO> updateProfile(UUID profileId, UpdateProfileDTO updateDto) {
        Optional<Profile> profileOpt = profileRepository.findById(profileId);

        if (profileOpt.isEmpty()) {
            return Optional.empty();
        }

        Profile profile = profileOpt.get();

        profile.setEmail(updateDto.getEmail());
        profile.setAreNotificationsMuted(updateDto.isAreNotificationsMuted());

        if (updateDto.getPassword() != null && !updateDto.getPassword().isEmpty()) {
            String hashedPassword = updateDto.getPassword(); // add hashing later on
            profile.setPassword(hashedPassword);
        }

        sellerRepository.findByProfile(profile).ifPresent(seller -> {
            seller.setName(updateDto.getName());
            seller.setSurname(updateDto.getSurname());
            seller.setCity(new City(updateDto.getCity()));
            seller.setAddress(updateDto.getAddress());
            seller.setTelephoneNumber(updateDto.getTelephoneNumber());
            seller.setDescription(updateDto.getDescription());
            sellerRepository.save(seller);
        });

        eventOrganizerRepository.findByProfile(profile).ifPresent(organizer -> {
            organizer.setName(updateDto.getName());
            organizer.setSurname(updateDto.getSurname());
            organizer.setCity(new City(updateDto.getCity()));
            organizer.setAddress(updateDto.getAddress());
            organizer.setTelephoneNumber(updateDto.getTelephoneNumber());
            eventOrganizerRepository.save(organizer);
        });

        profileRepository.save(profile);

        return getExtendedProfileById(profileId);
    }
    public Profile createEmptyGuestProfile(String email) {
        Optional<Role> role = roleRepository.findByName("ROLE_GUEST");
        Profile profile = new Profile();
        if(role.isEmpty()){
            return profile;
        }
        profile.BuildProfile(email, "Password123!", true, false, false, role.get());
        profile.setBlockedUsers(new ArrayList<>());

        return profileRepository.save(profile);
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
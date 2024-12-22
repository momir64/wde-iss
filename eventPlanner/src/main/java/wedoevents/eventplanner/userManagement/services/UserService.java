package wedoevents.eventplanner.userManagement.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.repositories.userTypes.AdminRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.GuestRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.SellerRepository;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.models.userTypes.Admin;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;


import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Transactional
    public void deleteAllUsersByProfile(Profile profile) {
        eventOrganizerRepository.deleteByProfile(profile);
        guestRepository.deleteByProfile(profile);
        sellerRepository.deleteByProfile(profile);
    }

    public UUID getUserId(UUID profileId){
        Optional<Guest> g = guestRepository.findByProfileId(profileId);
        if(g.isPresent()){
            return g.get().getId();
        }

        Optional<Admin> a = adminRepository.findByProfileId(profileId);
        if(a.isPresent()){
            return a.get().getId();
        }

        Optional<Seller> s = sellerRepository.findByProfileId(profileId);
        if(s.isPresent()){
            return s.get().getId();
        }

        Optional<EventOrganizer> eo = eventOrganizerRepository.findByProfileId(profileId);
        return eo.map(EventOrganizer::getId).orElse(null);

    }
}

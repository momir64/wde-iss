package wedoevents.eventplanner.userManagement.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.GuestRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.SellerRepository;

@Service
public class UserService {
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Transactional
    public void deleteAllUsersByProfile(Profile profile) {
        eventOrganizerRepository.deleteByProfile(profile);
        guestRepository.deleteByProfile(profile);
        sellerRepository.deleteByProfile(profile);
    }
}

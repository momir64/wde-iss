package wedoevents.eventplanner.userManagement.services.userTypes;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.repositories.userTypes.GuestRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest saveGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public Optional<Guest> getGuestById(UUID id) {
        return guestRepository.findById(id);
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public void deleteGuest(UUID id) {
        guestRepository.deleteById(id);
    }

    public Guest createOrUpdateGuest(Guest guest) {
        if (guest.getId() != null && guestRepository.existsById(guest.getId())) {
            Guest existingGuest = guestRepository.findById(guest.getId()).orElse(null);
            if (existingGuest != null) {
                BeanUtils.copyProperties(guest, existingGuest, "id");
                return guestRepository.save(existingGuest);
            }
        }
        // Create new
        return guestRepository.save(guest);
    }
}
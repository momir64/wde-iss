package wedoevents.eventplanner.userManagement.services.userTypes;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.repositories.userTypes.GuestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestService {

    private final GuestRepository guestRepository;
    private final EventRepository eventRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository, EventRepository eventRepository) {
        this.guestRepository = guestRepository;
        this.eventRepository = eventRepository;
    }

    public Guest saveGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public Optional<Guest> getGuestById(UUID id) {
        return guestRepository.findById(id);
    }

    public Optional<Guest> getGuestByEmail(String email) {
        return guestRepository.findByEmail(email);
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

    public Optional<Guest> getGuestByProfile(Profile profile) {
        return guestRepository.findByProfile(profile);
    }

    public void deleteByProfile(Profile profile) {
        guestRepository.deleteByProfile(profile);
    }

    public boolean addInvitation(Guest guest, Event event) {
        if (guest.getInvitedEvents().contains(event)) {
            return false;
        }
        guest.getInvitedEvents().add(event);
        guestRepository.save(guest);
        return true;
    }

    public boolean confirmInvitation(Guest guest, UUID eventId) {

        Event eventToConfirm = guest.getInvitedEvents()
                                    .stream()
                                    .filter(event -> event.getId().equals(eventId))
                                    .findFirst()
                                    .orElse(null);

        if (eventToConfirm == null) {
            return false;
        }

        guest.getInvitedEvents().remove(eventToConfirm);
        guest.getAcceptedEvents().add(eventToConfirm);
        guestRepository.save(guest);
        return true;
    }

    public Guest createGuestForEvent(Profile profile, Event event) {
        Guest guest = new Guest();

        guest.setProfile(profile);

        guest.setFavouriteEvents(new ArrayList<>());
        guest.setInvitedEvents(new ArrayList<>(List.of(event)));
        guest.setAcceptedEvents(new ArrayList<>());

        return guestRepository.save(guest);
    }

    public Optional<Event> getInvitedEvent(Guest guest, UUID eventId) {
        return guest.getInvitedEvents().stream().filter(event -> event.getId().equals(eventId)).findFirst();
    }
    public void confirmInvitation(Event event, Guest guest, boolean decision) {

        if(decision){
            guest.getInvitedEvents().remove(event);
            guest.getAcceptedEvents().add(event);
            guestRepository.save(guest);
            event.setGuestCount(event.getGuestCount() + 1);
            eventRepository.save(event);
        }else{
            guest.getAcceptedEvents().remove(event);
            guestRepository.save(guest);
        }
    }
    public long getAcceptedGuestCount(UUID eventId) {
        return guestRepository.countGuestsByAcceptedEventId(eventId);
    }

    public List<Guest> getGuestsByInvitedEventId(UUID eventId) {
        return guestRepository.findGuestsByInvitedEventId(eventId);
    }

    public List<Guest> getGuestsByAcceptedEventId(UUID eventId) {
        return guestRepository.findGuestsByAcceptedEventId(eventId);
    }

}
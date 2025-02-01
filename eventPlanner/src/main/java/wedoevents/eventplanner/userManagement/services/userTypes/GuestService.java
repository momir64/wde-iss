package wedoevents.eventplanner.userManagement.services.userTypes;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.CalendarEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.services.EventService;
import wedoevents.eventplanner.shared.services.mappers.CalendarEventMapper;
import wedoevents.eventplanner.userManagement.dtos.EvenReviewResponseDTO;
import wedoevents.eventplanner.userManagement.dtos.JoinEventDTO;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.repositories.userTypes.GuestRepository;
import wedoevents.eventplanner.userManagement.services.EventReviewService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestRepository guestRepository;
    private final EventRepository eventRepository;
    private final EventReviewService eventReviewService;

    @Autowired
    public GuestService(GuestRepository guestRepository, EventRepository eventRepository, EventReviewService eventReviewService) {
        this.guestRepository = guestRepository;
        this.eventRepository = eventRepository;
        this.eventReviewService = eventReviewService;
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
    public boolean checkIfGuestIsInvitedOrAccepted(Guest guest, UUID eventId) {
        if(guest != null) {
            return guest.getInvitedEvents().stream().anyMatch(event -> event.getId().equals(eventId)) ||
                    guest.getAcceptedEvents().stream().anyMatch(event -> event.getId().equals(eventId));
        }else{
            List<Guest> guests = guestRepository.findAll();
            return guests.stream()
                    .anyMatch(g -> g.getInvitedEvents().stream().anyMatch(event -> event.getId().equals(eventId)) ||
                            g.getAcceptedEvents().stream().anyMatch(event -> event.getId().equals(eventId)));
        }
    }
    public boolean isEventFavorited(UUID guestId, UUID eventId) {
        Optional<Guest> guest = guestRepository.findById(guestId);
        if(guest.isEmpty()){
            return false;
        }
        return guest.get().getFavouriteEvents().stream().anyMatch(event -> event.getId().equals(eventId));
    }
    public boolean isEventAccepted(UUID guestId, UUID eventId) {
        Optional<Guest> guest = guestRepository.findById(guestId);
        if(guest.isEmpty()){
            return false;
        }
        return guest.get().getAcceptedEvents().stream().anyMatch(event -> event.getId().equals(eventId));
    }
    public boolean joinEvent(JoinEventDTO joinEventDTO) {
        Guest guest = guestRepository.findById(joinEventDTO.getGuestId()).orElse(null);
        if(guest == null){
            return false;
        }
        Event event = eventRepository.findById(joinEventDTO.getEventId()).orElse(null);
        if(event == null){
            return false;
        }
        if(guest.getAcceptedEvents().contains(event)){
            guest.getAcceptedEvents().remove(event);
        }else{
            guest.getAcceptedEvents().add(event);
        }
        guestRepository.save(guest);
        return true;
    }
    public List<EventComplexViewDTO> getFavoriteEvents(UUID guestId) {
        Guest guest = guestRepository.findById(guestId).orElse(null);
        if(guest == null){
            return null;
        }
        List<Event> events = guest.getFavouriteEvents();
        List<EventComplexViewDTO> response = new ArrayList<>();
        for(Event event : events){
            List<EvenReviewResponseDTO> reviews = eventReviewService.getAcceptedReviewsByEventId(event.getId());
            response.add(new EventComplexViewDTO(event,calculateAverageGrade(reviews)));
        }
        return response;
    }
    public boolean favoriteEvent(UUID guestId, UUID eventId) {
        Guest guest = guestRepository.findById(guestId).orElse(null);
        if(guest == null){
            return false;
        }
        Event event = eventRepository.findById(eventId).orElse(null);
        if(event == null){
            return false;
        }
        if(guest.getFavouriteEvents().contains(event)){
            guest.getFavouriteEvents().remove(event);
        }else{
            guest.getFavouriteEvents().add(event);
        }
        guestRepository.save(guest);
        return true;
    }

    public List<CalendarEventDTO> getCalendarEvents(UUID guestId) {
        Guest guest = guestRepository.findById(guestId).orElse(null);
        if(guest == null){
            return null;
        }
        List<Event> acceptedEvents = guest.getAcceptedEvents();
        return acceptedEvents.stream()
                .map(CalendarEventMapper::toCalendarEventDTO)
                .collect(Collectors.toList());
    }
    public double calculateAverageGrade(List<EvenReviewResponseDTO> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        OptionalDouble average = reviews.stream()
                .mapToInt(EvenReviewResponseDTO::getGrade)
                .average();

        return average.orElse(0.0);
    }
}
package wedoevents.eventplanner.userManagement.controllers.userTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.CalendarEventDTO;
import wedoevents.eventplanner.userManagement.dtos.FavoriteListingDTO;
import wedoevents.eventplanner.userManagement.dtos.UserAdditionalInfoDTO;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/eventOrganizers")
public class EventOrganizerController {

    private final EventOrganizerService eventOrganizerService;

    @Autowired
    public EventOrganizerController(EventOrganizerService eventOrganizerService) {
        this.eventOrganizerService = eventOrganizerService;
    }

    @PutMapping("/{id}/favorite-listings")
    public ResponseEntity<?> processUuid(@RequestBody FavoriteListingDTO request) {
        try {
            // call update event organizer
            return ResponseEntity.ok("Listing favorited successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to favorite listing");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PostMapping
    public ResponseEntity<EventOrganizer> createEventOrganizer(@RequestBody EventOrganizer eventOrganizer) {
        EventOrganizer savedAttempt = eventOrganizerService.saveEventOrganizer(eventOrganizer);
        return ResponseEntity.ok(savedAttempt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventOrganizer> getEventOrganizerById(@PathVariable UUID id) {
        return eventOrganizerService.getEventOrganizerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventOrganizer>> getAllEventOrganizers() {
        return ResponseEntity.ok(eventOrganizerService.getAllEventOrganizers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventOrganizer(@PathVariable UUID id) {
        eventOrganizerService.deleteEventOrganizer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<?> getOrganizerAdditionalInfo(@PathVariable UUID profileId) {
        Optional<EventOrganizer> organizerOptional = eventOrganizerService.getEventOrganizerByProfileId(profileId);
        if(organizerOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        EventOrganizer organizer = organizerOptional.get();
        return ResponseEntity.ok(new UserAdditionalInfoDTO(organizer.getName(),organizer.getSurname()));
    }
    @GetMapping("/{id}/calendar")
    public ResponseEntity<?> getOrganizerCalendar(@PathVariable UUID id) {
        List<CalendarEventDTO> response = eventOrganizerService.getCalendarEvents(id);
        if(response.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
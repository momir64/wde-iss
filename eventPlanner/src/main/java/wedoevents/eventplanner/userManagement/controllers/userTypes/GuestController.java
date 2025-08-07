package wedoevents.eventplanner.userManagement.controllers.userTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.CalendarEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.userManagement.dtos.BasicGuestDTO;
import wedoevents.eventplanner.userManagement.dtos.FavoriteEventDTO;
import wedoevents.eventplanner.userManagement.dtos.FavoritesRequestDTO;
import wedoevents.eventplanner.userManagement.dtos.JoinEventDTO;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/guests")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<BasicGuestDTO> getGuestByEmail(@PathVariable String email) {
        return guestService.getGuestByEmail(email).map(BasicGuestDTO::from)
                           .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest) {
        Guest savedAttempt = guestService.saveGuest(guest);
        return ResponseEntity.ok(savedAttempt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable UUID id) {
        return guestService.getGuestById(id)
                           .map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable UUID id) {
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/join-event")
    public ResponseEntity<?> joinEvent(@RequestBody JoinEventDTO request) {
        if(guestService.joinEvent(request)) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
    }
    @GetMapping("/{id}/favorite-events")
    public ResponseEntity<?> getFavoriteEvents(@PathVariable UUID id) {
        List<EventComplexViewDTO> events = guestService.getFavoriteEvents(id);
        if(events == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(events);
        }
    }
    @PutMapping("/favorite-events")
    public ResponseEntity<?> favoriteEvent(@RequestBody FavoritesRequestDTO request) {
        if(guestService.favoriteEvent(request.getUserId(), request.getFavoriteItemId())) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
    }
    @GetMapping("/{id}/calendar")
    public ResponseEntity<?> getGuestCalendar(@PathVariable UUID id) {
        List<CalendarEventDTO> response = guestService.getCalendarEvents(id);
        if(response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
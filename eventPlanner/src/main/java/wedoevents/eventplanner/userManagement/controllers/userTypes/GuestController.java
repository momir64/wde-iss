package wedoevents.eventplanner.userManagement.controllers.userTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.dtos.FavoriteEventDTO;
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

    @PutMapping("/{id}/favorite-events")
    public ResponseEntity<?> processUuid(@RequestBody FavoriteEventDTO request) {
        try {
            // call update guest
            return ResponseEntity.ok("Event favorited successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to favorite event");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
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
}
package wedoevents.eventplanner.userManagement.controllers.userTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.CalendarEventDTO;
import wedoevents.eventplanner.userManagement.dtos.userTypes.SellerDetailedViewDTO;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.services.userTypes.SellerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sellers")
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        Seller savedAttempt = sellerService.saveSeller(seller);
        return ResponseEntity.ok(savedAttempt);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable UUID id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/detailed-view")
    public ResponseEntity<?> getSellerDetailedView(@PathVariable UUID id) {
        SellerDetailedViewDTO response = sellerService.getSellerDetailedView(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/calendar")
    public ResponseEntity<?> getSellerCalendar(@PathVariable UUID id) {
        List<CalendarEventDTO> response = sellerService.getCalendarEvents(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
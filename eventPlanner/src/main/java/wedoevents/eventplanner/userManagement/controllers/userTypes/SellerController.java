package wedoevents.eventplanner.userManagement.controllers.userTypes;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.dtos.SellerDetailedInfoDTO;
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

    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerDetailedInfoDTO> getSellerDetailedInfo(@PathVariable UUID sellerId) {
        try {
            return ResponseEntity.ok(sellerService.getSellerDetailedInfo(sellerId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
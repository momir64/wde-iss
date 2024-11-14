package wedoevents.eventplanner.userManagement.services.userTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.repositories.userTypes.SellerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Seller saveSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Optional<Seller> getSellerById(UUID id) {
        return sellerRepository.findById(id);
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public void deleteSeller(UUID id) {
        sellerRepository.deleteById(id);
    }
}
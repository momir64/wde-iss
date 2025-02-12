package wedoevents.eventplanner.listingManagement.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import wedoevents.eventplanner.listingManagement.dtos.ListingDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.listingManagement.repositories.ListingRepository;
import wedoevents.eventplanner.userManagement.models.PendingStatus;
import wedoevents.eventplanner.userManagement.services.ListingReviewService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ListingReviewService listingReviewService;

    public List<ListingDTO> getTopListings(String city) {
        List<ListingDTO> listings = listingRepository.getTopListings(city).stream().map(ListingDTO::new).toList();
        for(ListingDTO listing : listings) {
            listing.setRating(listingReviewService.calculateAverageGrade(listing.getId(), PendingStatus.APPROVED,listing.getType()==ListingType.PRODUCT));
        }
        listings = new ArrayList<>(listings.stream().collect(Collectors.toMap(ListingDTO::getId, Function.identity(), ListingDTO::appendImages)).values());
        listings.forEach(listing -> listing.getImages().sort(String::compareTo));
        return listings;
    }

    public Map<String, Object> getListings(String searchTerms, ListingType type, UUID category, Double minPrice, Double maxPrice, Double minRating, Double maxRating, String sortBy, String order, int page, int size) {
        List<Object[]> data = listingRepository.getListings(searchTerms, type == null ? null : type.toString(), category, minPrice, maxPrice,minRating,maxRating, sortBy, order, page, size);
        List<ListingDTO> listings = data.stream().map(ListingDTO::new).toList();
        listings = new ArrayList<>(listings.stream().collect(Collectors.toMap(ListingDTO::getId, Function.identity(), ListingDTO::appendImages)).values());
        if (order != null && order.equalsIgnoreCase("asc")) {
            if (sortBy != null && sortBy.equalsIgnoreCase("name"))
                listings.sort(Comparator.comparing(ListingDTO::getName));
            else if (sortBy != null && sortBy.equalsIgnoreCase("price"))
                listings.sort(Comparator.comparing(ListingDTO::getPrice));
            else
                listings.sort(Comparator.comparing(ListingDTO::getRating));
        } else if (order != null && order.equalsIgnoreCase("desc")) {
            if (sortBy != null && sortBy.equalsIgnoreCase("name"))
                listings.sort(Comparator.comparing(ListingDTO::getName).reversed());
            else if (sortBy != null && sortBy.equalsIgnoreCase("price"))
                listings.sort(Comparator.comparing(ListingDTO::getPrice).reversed());
            else
                listings.sort(Comparator.comparing(ListingDTO::getRating).reversed());
        }
        listings.forEach(listing -> listing.getImages().sort(String::compareTo));
        int totalPages = data.isEmpty() ? 1 : (int) Math.ceil((double) (Long) data.get(0)[data.get(0).length - 1] / size);
        for(ListingDTO listing : listings) {
            listing.setRating(listingReviewService.calculateAverageGrade(listing.getId(), PendingStatus.APPROVED,listing.getType()==ListingType.PRODUCT));
        }
        return Map.of("totalPages", totalPages, "content", listings);
    }

    public Map<String, Object> getListingsFromSeller(UUID sellerId, String searchTerms, ListingType type, UUID category, Double minPrice, Double maxPrice, Double minRating, Double maxRating, String sortBy, String order, int page, int size) {
        List<Object[]> data = listingRepository.getListingsFromSeller(sellerId, searchTerms, type == null ? null : type.toString(), category, minPrice, maxPrice,minRating,maxRating, sortBy, order, page, size);
        List<ListingDTO> listings = data.stream().map(ListingDTO::new).toList();
        listings = new ArrayList<>(listings.stream().collect(Collectors.toMap(ListingDTO::getId, Function.identity(), ListingDTO::appendImages)).values());
        for(ListingDTO listing : listings) {
            listing.setRating(listingReviewService.calculateAverageGrade(listing.getId(), PendingStatus.APPROVED,listing.getType()==ListingType.PRODUCT));
        }
        if (order != null && order.equalsIgnoreCase("asc")) {
            if (sortBy != null && sortBy.equalsIgnoreCase("name"))
                listings.sort(Comparator.comparing(ListingDTO::getName));
            else if (sortBy != null && sortBy.equalsIgnoreCase("price"))
                listings.sort(Comparator.comparing(ListingDTO::getPrice));
            else
                listings.sort(Comparator.comparing(ListingDTO::getRating));
        } else if (order != null && order.equalsIgnoreCase("desc")) {
            if (sortBy != null && sortBy.equalsIgnoreCase("name"))
                listings.sort(Comparator.comparing(ListingDTO::getName).reversed());
            else if (sortBy != null && sortBy.equalsIgnoreCase("price"))
                listings.sort(Comparator.comparing(ListingDTO::getPrice).reversed());
            else
                listings.sort(Comparator.comparing(ListingDTO::getRating).reversed());
        }
        int totalPages = data.isEmpty() ? 1 : (int) Math.ceil((double) (Long) data.get(0)[data.get(0).length - 1] / size);
        return Map.of("totalPages", totalPages, "content", listings);
    }
}

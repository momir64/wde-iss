package wedoevents.eventplanner.userManagement.services.userTypes;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.CalendarEventDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.listingManagement.dtos.ListingDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.dtos.VersionedProductDTO;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.productManagement.services.ProductService;
import wedoevents.eventplanner.serviceManagement.dtos.VersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;
import wedoevents.eventplanner.shared.services.mappers.CalendarEventMapper;
import wedoevents.eventplanner.userManagement.dtos.FavoritesRequestDTO;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;
import wedoevents.eventplanner.userManagement.services.ListingReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventOrganizerService {

    private final EventOrganizerRepository eventOrganizerRepository;
    private final ProductService productService;
    private final ServiceService serviceService;
    @Autowired
    public EventOrganizerService(EventOrganizerRepository eventOrganizerRepository, ProductService productService, ServiceService serviceService) {
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.productService = productService;
        this.serviceService = serviceService;
    }

    public EventOrganizer saveEventOrganizer(EventOrganizer eventOrganizer) {
        return eventOrganizerRepository.save(eventOrganizer);
    }

    public Optional<EventOrganizer> getEventOrganizerById(UUID id) {
        return eventOrganizerRepository.findById(id);
    }

    public List<EventOrganizer> getAllEventOrganizers() {
        return eventOrganizerRepository.findAll();
    }

    public void deleteEventOrganizer(UUID id) {
        eventOrganizerRepository.deleteById(id);
    }

    public EventOrganizer createOrUpdateEventOrganizer(EventOrganizer eventOrganizer) {
        if (eventOrganizer.getProfile() != null) {
            Optional<EventOrganizer> existingOrganizerOpt = eventOrganizerRepository.findByProfile(eventOrganizer.getProfile());

            if (existingOrganizerOpt.isPresent()) {
                EventOrganizer existingOrganizer = existingOrganizerOpt.get();
                BeanUtils.copyProperties(eventOrganizer, existingOrganizer, "id", "profile"); // Preserve id and profile
                return eventOrganizerRepository.save(existingOrganizer);
            }
        }
        // Create new if no existing organizer found
        return eventOrganizerRepository.save(eventOrganizer);
    }
    public void deleteByProfile(Profile profile){
        eventOrganizerRepository.deleteByProfile(profile);
    }
    public Optional<EventOrganizer> getEventOrganizerByEventId(UUID eventId) {
        return eventOrganizerRepository.findByEventId(eventId);
    }
    public Optional<EventOrganizer> getEventOrganizerByProfileId(UUID profileId) {
        return eventOrganizerRepository.findByProfileId(profileId);
    }

    public List<ListingDTO> getFavoriteListings(UUID organizerId) {
        EventOrganizer eventOrganizer = eventOrganizerRepository.findById(organizerId).orElse(null);
        if (eventOrganizer == null) {
            return null;
        }
        List<ListingDTO> response = new ArrayList<>();
        List<StaticProduct> favouriteproducts = eventOrganizer.getFavouriteProducts();
        for(StaticProduct product : favouriteproducts) {
            try{
                VersionedProductDTO versionedProduct = productService.getVersionedProductById(product.getStaticProductId());
                response.add(new ListingDTO(versionedProduct));
            }catch (Exception e) {
                // product is unavailable
            }
        }
        List<StaticService> favouriteservices = eventOrganizer.getFavouriteServices();
        for(StaticService service : favouriteservices) {
            try{
                VersionedServiceDTO versionedService = serviceService.getVersionedServiceById(service.getStaticServiceId());
                response.add(new ListingDTO(versionedService));
            }catch(Exception e) {
                // service is unavailable
            }
        }
        return response;
    }
    public boolean favoriteListing(FavoritesRequestDTO request){
        EventOrganizer eventOrganizer = eventOrganizerRepository.findById(request.getUserId()).orElse(null);
        if (eventOrganizer == null) {
            return false;
        }
        if(request.getListingType() == ListingType.PRODUCT){
            Optional<StaticProduct> product = productService.getStaticProductById(request.getFavoriteItemId());
            if (product.isEmpty()){
                return false;
            }
            if(eventOrganizer.getFavouriteProducts().contains(product.get())){
                eventOrganizer.getFavouriteProducts().remove(product.get());
            }else{
                eventOrganizer.getFavouriteProducts().add(product.get());
            }
            eventOrganizerRepository.save(eventOrganizer);
            return true;
        }else{
            Optional<StaticService> service = serviceService.getStaticServiceById(request.getFavoriteItemId());
            if (service.isEmpty()){
                return false;
            }
            if(eventOrganizer.getFavouriteServices().contains(service.get())){
                eventOrganizer.getFavouriteServices().remove(service.get());
            }else{
                eventOrganizer.getFavouriteServices().add(service.get());
            }
            eventOrganizerRepository.save(eventOrganizer);
            return true;
        }
    }
    public List<CalendarEventDTO> getCalendarEvents(UUID organizerId) {
        EventOrganizer eventOrganizer = eventOrganizerRepository.findById(organizerId).orElse(null);
        if (eventOrganizer == null) {
            return null;
        }
        List<Event> myEvents = eventOrganizer.getMyEvents();
        return myEvents.stream()
                .map(CalendarEventMapper::toCalendarEventDTO)
                .collect(Collectors.toList());
    }
}
package wedoevents.eventplanner.eventManagement.controllers;

import com.google.api.Http;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wedoevents.eventplanner.eventManagement.dtos.*;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.services.EventService;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;
import wedoevents.eventplanner.shared.services.pdfService.PdfGeneratorService;
import wedoevents.eventplanner.userManagement.dtos.ReviewDistributionDTO;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.services.EventReviewService;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
@Validated
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;
    private final ImageService imageService;
    private final PdfGeneratorService pdfService;
    private final GuestService guestService;
    private final EventReviewService eventReviewService;
    @Autowired
    public EventController(EventService eventService, ImageService imageService, PdfGeneratorService pdfService, GuestService guestService, EventReviewService eventReviewService) {
        this.eventService = eventService;
        this.imageService = imageService;
        this.pdfService = pdfService;
        this.guestService = guestService;
        this.eventReviewService = eventReviewService;
    }


    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid CreateEventDTO createEventDTO) {
        try {
            EventComplexViewDTO createdEvent = eventService.createEvent(createEventDTO);
            return ResponseEntity.ok(createdEvent);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("Error processing request");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }
    @PutMapping
    public ResponseEntity<?> updateEvent(@RequestBody CreateEventDTO createEventDTO){
        try {
            eventService.updateEvent(createEventDTO);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("Error processing request");
        }
    }
    @PutMapping("/images")
    public ResponseEntity<?> putProfileImage(@RequestParam("images") List<MultipartFile> images,
                                             @RequestParam("eventId") UUID eventId){
        try {
            return ResponseEntity.ok(eventService.putEventImages(images, eventId));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not save images");
        }
    }
    @GetMapping("/{eventOrganizerId}/my-events")
    public ResponseEntity<List<EventComplexViewDTO>> getEventsFromOrganizer(@PathVariable UUID eventOrganizerId) {
        try {
            List<EventComplexViewDTO> events = eventService.getEventsFromOrganizer(eventOrganizerId);
            return ResponseEntity.ok(events);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{eventOrganizerId}/my-events/{eventId}")
    public ResponseEntity<EventEditViewDTO> getEventFromOrganizer(@PathVariable UUID eventOrganizerId, @PathVariable UUID eventId) {
        EventEditViewDTO event = eventService.getEventFromOrganizer(eventOrganizerId, eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }
    @GetMapping("/top")
    public ResponseEntity<?> getTopEvents(@RequestParam(value = "city", required = false) String city) {
        try {
            return ResponseEntity.ok(eventService.getTopEvents(city));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping
    public ResponseEntity<?> searchEvents(@RequestParam(value = "searchTerms", required = false) String searchTerms,
                                          @RequestParam(value = "city", required = false) String city,
                                          @RequestParam(value = "type", required = false) UUID eventTypeId,
                                          @RequestParam(value = "minRating", required = false) Double minRating,
                                          @RequestParam(value = "maxRating", required = false) Double maxRating,
                                          @RequestParam(value = "organizerId", required = false) UUID organizerId,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateRangeStart,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateRangeEnd,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false) String order,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(eventService.searchEvents(searchTerms, city, eventTypeId, minRating, maxRating, dateRangeStart, dateRangeEnd, sortBy, order, page, size,organizerId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping(value = "/{id}/images/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getProfileImage(@PathVariable("id") UUID id, @PathVariable("image_name") String imageName) {
        try {
            ImageLocationConfiguration config = new ImageLocationConfiguration("event", id);
            Optional<byte[]> image = imageService.getImage(imageName, config);
            if (image.isEmpty())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
            return ResponseEntity.ok().body(image.get());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PostMapping("/agenda")
    public ResponseEntity<List<UUID>> createAgenda(@Validated @RequestBody EventActivitiesDTO eventActivitiesDTO) {
        return ResponseEntity.ok().body(eventService.createAgenda(eventActivitiesDTO));
    }
    @PutMapping("/agenda")
    public ResponseEntity<?> updateAgenda(@Validated @RequestBody EventActivitiesDTO eventActivitiesDTO) {
        return eventService.updateAgenda(eventActivitiesDTO) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    @GetMapping("/agenda/{eventId}")
    public ResponseEntity<List<EventActivityDTO>> getAgenda(@PathVariable UUID eventId) {
        List<EventActivityDTO> response = eventService.getAgenda(eventId);
        if(response == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable("id") UUID id) {
        Optional<Event> event = eventService.getEventById(id);
        if (event.isPresent()) {
            List<Guest> invitedGuests = guestService.getGuestsByInvitedEventId(id);
            List<Guest> acceptedGuests = guestService.getGuestsByAcceptedEventId(id);
            ReviewDistributionDTO reviewDistribution = eventReviewService.getReviewCounts(id);
            byte[] pdfContent = pdfService.generateEventPdf(event.get(),invitedGuests,acceptedGuests,reviewDistribution);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "event-details.pdf");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfContent);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }
    @GetMapping("/public")
    public ResponseEntity<?> getPublicEvents() {
        List<EventAdminViewDTO> publicEvents = eventService.getAllPublicEvents();
        return ResponseEntity.ok(publicEvents);
    }

    @GetMapping("/{id}/detailed-view/{isGuest}/{userId}")
    public ResponseEntity<?> getDetailedView(@PathVariable("id") UUID eventId, @PathVariable("isGuest") boolean isGuest, @PathVariable("userId") UUID userId) {
        EventDetailedViewDTO event = eventService.getDetailedEvent(eventId,isGuest,userId);
        if(event == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }
        return ResponseEntity.ok().body(event);
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<?> getImages(@PathVariable("id") UUID eventId) {
        Optional<Event> event = eventService.getEventById(eventId);
        if (event.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }
        try {
            List<String> imageNames = event.get().getImages();
            ImageLocationConfiguration config = new ImageLocationConfiguration("event", eventId);
            List<byte[]> images = new ArrayList<>();
            for(String imageName : imageNames) {
                Optional<byte[]> image = imageService.getImage(imageName, config);
                if (image.isEmpty())
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
                images.add(image.get());
            }
            return ResponseEntity.ok().body(new EventImagesDTO(images));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }
    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") UUID eventId, @PathVariable("userId") UUID userId) {
        if(eventService.deleteEvent(eventId,userId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Event deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
    }
}
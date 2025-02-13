package wedoevents.eventplanner.eventManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.BuyServiceDTO;
import wedoevents.eventplanner.eventManagement.dtos.CreateServiceBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.dtos.ServiceBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.services.EventService;
import wedoevents.eventplanner.eventManagement.services.ServiceBudgetItemService;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;
import wedoevents.eventplanner.shared.Exceptions.BuyServiceException;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;
import wedoevents.eventplanner.shared.services.emailService.IEmailService;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;
import wedoevents.eventplanner.userManagement.services.userTypes.SellerService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service-budget-items")
public class ServiceBudgetItemController {

    private final ServiceBudgetItemService serviceBudgetItemService;
    private final EventOrganizerService eventOrganizerService;
    private final SellerService sellerService;
    private final ServiceService serviceService;
    private final EventService eventService;
    private final String recipientEmail = "uvoduvod1@gmail.com";
    private final IEmailService emailService;


    @Autowired
    public ServiceBudgetItemController(ServiceBudgetItemService serviceBudgetItemService, EventOrganizerService eventOrganizerService,
                                       SellerService sellerService, ServiceService serviceService, EventService eventService, @Qualifier("sendGridEmailService") IEmailService emailService) {
        this.serviceBudgetItemService = serviceBudgetItemService;
        this.eventOrganizerService = eventOrganizerService;
        this.sellerService = sellerService;
        this.serviceService = serviceService;
        this.eventService = eventService;
        this.emailService = emailService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductBudgetItem(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(serviceBudgetItemService.getServiceBudgetItem(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{serviceId}/slots")
    public ResponseEntity<?> getSlots(@PathVariable("serviceId") UUID serviceId) {
        try {
            UUID organizerId = UUID.fromString("1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236"); // todo: for now fixed organizer
            return ResponseEntity.ok(serviceBudgetItemService.getSlots(serviceId, organizerId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createServiceBudgetItem(@RequestBody CreateServiceBudgetItemDTO serviceBudgetItem) {
        // todo clean up this comment after checking
//  if (adding new budget item - event doesn't have item with that category) {
//        return new ResponseEntity<>(serviceBudgetItem, HttpStatus.CREATED);
//  } else if (error - some id isn't null but doesn't exist) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.NOT_FOUND);
//  } else if (error - user doesn't have necessary permissions) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.FORBIDDEN);
//  } else if (changing budget item max price, category or service - item id isn't null and exists) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.OK);
//  } else if (buying service - service id isn't null, event has item with that category and without service id) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.OK);
//  } else if (buying service - service id isn't null, event doesn't have item with that category) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.CREATED);
//  } else if (buying service error - service id isn't null, event has item with that category and with service id) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.BAD_REQUEST);
//  }
        try {
            return ResponseEntity.ok(serviceBudgetItemService.createServiceBudgetItem(serviceBudgetItem));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BuyServiceException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyService(@RequestBody BuyServiceDTO buyServiceDTO) {
        try {
            ServiceBudgetItemDTO budgetItem = serviceBudgetItemService.buyService(buyServiceDTO);
            Optional<EventOrganizer> organizer = eventOrganizerService.getEventOrganizerByEventId(buyServiceDTO.getEventId());
            Optional<Event> event = eventService.getEventById(buyServiceDTO.getEventId());
            Optional<VersionedService> versionedService = serviceService.getLatestByStaticServiceIdAndLatestVersion(buyServiceDTO.getServiceId());
            Optional<Seller> seller = sellerService.getSellerByServiceId(versionedService.get().getStaticServiceId());
            if(seller.isEmpty() || organizer.isEmpty() || event.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
            }
            this.emailService.sendEventOrganizerServiceReservationEmail(recipientEmail,event.get(),versionedService.get(),organizer.get());
            this.emailService.sendSellerReservationEmail(recipientEmail,event.get(),versionedService.get(),seller.get());
            return ResponseEntity.ok(budgetItem);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BuyServiceException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }

    }

    @DeleteMapping ("/{eventId}/{serviceCategoryId}")
    public ResponseEntity<?> deleteEventEmptyServiceCategoryFromBudget(@PathVariable UUID eventId, @PathVariable UUID serviceCategoryId) {
        try {
            serviceBudgetItemService.deleteEventEmptyServiceCategoryFromBudget(eventId, serviceCategoryId);
            return ResponseEntity.noContent().build();
        } catch (EntityCannotBeDeletedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Service category has reserved services");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
package wedoevents.eventplanner.eventManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wedoevents.eventplanner.eventManagement.dtos.GuestListDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.services.EventService;
import wedoevents.eventplanner.shared.services.emailService.IEmailService;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.repositories.RegistrationAttemptRepository;
import wedoevents.eventplanner.userManagement.services.ProfileService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/invitations")
public class InvitationsController {
    private final String recipientEmail = "uvoduvod1@gmail.com";
    private final IEmailService emailService;
    private final ProfileService profileService;
    private final GuestService guestService;
    private final EventService eventService;
    private final RegistrationAttemptRepository registrationAttemptRepository;

    @Autowired
    public InvitationsController(ProfileService profileService, EventService eventService, @Qualifier("sendGridEmailService")  IEmailService emailService, GuestService guestService, RegistrationAttemptRepository registrationAttemptRepository) {
        this.profileService = profileService;
        this.eventService = eventService;
        this.emailService = emailService;
        this.guestService = guestService;
        this.registrationAttemptRepository = registrationAttemptRepository;
    }

    @PostMapping
    public ResponseEntity<?> createInvitations(@RequestBody GuestListDTO request){
        Optional<Event> event = eventService.getEventById(request.getEventId());
        if(event.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event id");
        }
        if(event.get().getIsPublic()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Event is public");
        }
        List<String> emails = request.getEmails();
        if (emails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty guest list");
        }
        int guestCount = 0;


        for(String email : emails) {
            Optional<Profile> profile = profileService.findProfileByEmail(email);
            if(profile.isEmpty()) {
                //create account, send email
                Profile newProfile = profileService.createEmptyProfile(email);
                guestService.createGuestForEvent(newProfile, event.get());
                try{
                    String response = emailService.sendInvitationEmail(recipientEmail, email ,event.get().getName(),request.getOrganizerName(),request.getOrganizerSurname());
                    guestCount++;
                    //TODO log
                }catch (Exception e){
                    //TODO log
                }
            }else{
                Optional<Guest> guest = guestService.getGuestByProfile(profile.get());
                if(guest.isPresent()) {
                    guestService.addInvitation(guest.get(), event.get());
                    guestCount++;
                    // optionally send notification, email etc.
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(guestCount);
    }
}

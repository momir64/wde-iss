package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.models.RegistrationAttempt;
import wedoevents.eventplanner.userManagement.services.RegistrationAttemptService;

import java.util.List;

@RestController
@RequestMapping("/api/registrationAttempts")
public class RegistrationAttemptController {

    private final RegistrationAttemptService registrationAttemptService;

    @Autowired
    public RegistrationAttemptController(RegistrationAttemptService registrationAttemptService) {
        this.registrationAttemptService = registrationAttemptService;
    }

    @PostMapping
    public ResponseEntity<RegistrationAttempt> createRegistrationAttempt(@RequestBody RegistrationAttempt registrationAttempt) {
        RegistrationAttempt savedAttempt = registrationAttemptService.saveRegistrationAttempt(registrationAttempt);
        return ResponseEntity.ok(savedAttempt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationAttempt> getRegistrationAttemptById(@PathVariable Long id) {
        return registrationAttemptService.getRegistrationAttemptById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RegistrationAttempt>> getAllRegistrationAttempts() {
        return ResponseEntity.ok(registrationAttemptService.getAllRegistrationAttempts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistrationAttempt(@PathVariable Long id) {
        registrationAttemptService.deleteRegistrationAttempt(id);
        return ResponseEntity.noContent().build();
    }
}
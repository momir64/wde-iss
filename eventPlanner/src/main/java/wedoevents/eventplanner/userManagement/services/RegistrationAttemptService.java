package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.RegistrationAttempt;
import wedoevents.eventplanner.userManagement.repositories.RegistrationAttemptRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationAttemptService {

    private final RegistrationAttemptRepository registrationAttemptRepository;

    @Autowired
    public RegistrationAttemptService(RegistrationAttemptRepository registrationAttemptRepository) {
        this.registrationAttemptRepository = registrationAttemptRepository;
    }

    public RegistrationAttempt saveRegistrationAttempt(RegistrationAttempt registrationAttempt) {
        return registrationAttemptRepository.save(registrationAttempt);
    }

    public Optional<RegistrationAttempt> getRegistrationAttemptById(Long id) {
        return registrationAttemptRepository.findById(id);
    }

    public List<RegistrationAttempt> getAllRegistrationAttempts() {
        return registrationAttemptRepository.findAll();
    }

    public void deleteRegistrationAttempt(Long id) {
        registrationAttemptRepository.deleteById(id);
    }
    public Optional<RegistrationAttempt> getMostRecentRegistrationAttemptByProfileId(UUID profileId) {
        return registrationAttemptRepository.findTopByProfileIdOrderByTimeDesc(profileId);
    }
}
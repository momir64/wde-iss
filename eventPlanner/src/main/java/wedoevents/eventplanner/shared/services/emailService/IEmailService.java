package wedoevents.eventplanner.shared.services.emailService;

public interface IEmailService {
    public String sendEmail(String to, String subject, String htmlContent) throws Exception;
    public String sendVerificationEmail(String email, String name, String surname, String registrationAttemptId, String profileId) throws Exception;
}

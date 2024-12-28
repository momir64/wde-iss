package wedoevents.eventplanner.shared.services.emailService;

import java.io.IOException;

public interface IEmailService {
    public String sendEmail(String to, String subject, String htmlContent) throws Exception;
    public String sendVerificationEmail(String email, String userEmail, String name, String surname, String registrationAttemptId, String profileId) throws Exception;
    public String sendQuickRegistrationEmail(String email, String guestEmail, String event, String organizerName, String organizerSurname,String profileId) throws Exception;
    public String sendEventInvitationEmail(String email, String guestEmail, String event, String organizerName, String organizerSurname, String profileId, String eventId) throws IOException;
}

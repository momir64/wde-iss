package wedoevents.eventplanner.shared.services.emailService.sendGrid;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.shared.services.emailService.IEmailService;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier("sendGridEmailService")
public class SendGridEmailService implements IEmailService {
    private final SendGrid sendGrid;

    @Autowired
    public SendGridEmailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public String sendEmail(String to, String subject, String htmlContent) throws IOException {
        Email from = new Email("usi379538@gmail.com");
        Email toEmail = new Email(to);
        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            return "Email sent! Status Code: " + response.getStatusCode();
        } catch (IOException ex) {
            throw ex;
        }
    }

    public String sendVerificationEmail(String email, String userEmail, String name, String surname, String registrationAttemptId, String profileId) throws IOException {
        String templatePath =  "templates/VerificationView.html";
        String verificationUrl = "http://localhost:8080/api/v1/registrationAttempts/verify/" + registrationAttemptId + "/" + profileId; // Generate unique link5

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("email", userEmail);
        placeholders.put("name", name);
        placeholders.put("surname", surname);
        placeholders.put("verificationUrl", verificationUrl);

        String htmlContent = loadTemplate(templatePath, placeholders);
        return sendEmail(email, "Verify Your Registration", htmlContent); // Sending email as HTML
    }

    public String sendQuickRegistrationEmail(String email,String guestEmail, String event, String organizerName, String organizerSurname, String profileId) throws IOException {
        String templatePath = "templates/FastRegistration.html";
        String registrationUrl = "http://localhost:8080/api/v1/registrationAttempts/fast-registration/" + profileId;
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("email", guestEmail);
        placeholders.put("event", event);
        placeholders.put("organizerName", organizerName);
        placeholders.put("organizerSurname", organizerSurname);
        placeholders.put("registrationUrl", registrationUrl);
        String htmlContent = loadTemplate(templatePath, placeholders);
        return sendEmail(email, "Quick registration", htmlContent);
    }

    public String sendEventInvitationEmail(String email, String guestEmail, String event, String organizerName, String organizerSurname, String profileId, String eventId) throws IOException {
        String templatePath = "templates/EventInvitation.html";
        String confirmationUrl = "http://localhost:8080/api/v1/invitations/confirm/" + eventId + "/" + profileId;
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("email", guestEmail);
        placeholders.put("event", event);
        placeholders.put("organizerName", organizerName);
        placeholders.put("organizerSurname", organizerSurname);
        placeholders.put("confirmationUrl", confirmationUrl);
        String htmlContent = loadTemplate(templatePath, placeholders);
        return sendEmail(email, "Quick registration", htmlContent);
    }

    public String sendEventOrganizerServiceReservationEmail(String email, Event event, VersionedService versionedService, EventOrganizer eventOrganizer) throws IOException{
        String templatePath = "templates/EventOrganizerReservation.html";
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", eventOrganizer.getName());
        placeholders.put("surname", eventOrganizer.getSurname());
        placeholders.put("service", versionedService.getName());
        placeholders.put("event", event.getName());
        String htmlContent = loadTemplate(templatePath, placeholders);
        return sendEmail(email, "Reservation confirmation", htmlContent);
    }

    public String sendSellerReservationEmail(String email, Event event, VersionedService versionedService, Seller seller) throws IOException{
        String templatePath = "templates/SellerReservation.html";
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", seller.getName());
        placeholders.put("surname", seller.getSurname());
        placeholders.put("service", versionedService.getName());
        placeholders.put("event", event.getName());
        String htmlContent = loadTemplate(templatePath, placeholders);
        return sendEmail(email, "Reservation confirmation", htmlContent);
    }

    public String loadTemplate(String templatePath, Map<String, String> placeholders) throws IOException {
        ClassPathResource resource = new ClassPathResource(templatePath);
        String template = new String(resource.getInputStream().readAllBytes());
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }
}

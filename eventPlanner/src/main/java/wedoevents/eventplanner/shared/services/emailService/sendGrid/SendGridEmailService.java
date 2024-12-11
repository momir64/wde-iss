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
import wedoevents.eventplanner.shared.services.emailService.IEmailService;

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

    public String sendInvitationEmail(String email,String guestEmail, String event, String organizerName, String organizerSurname) throws IOException {
        String templatePath =  "templates/EventInvitation.html";
        //TODO change url
        String invitationUrl = "http://localhost:8080/api/v1/profiles/36832425-0f2f-43a4-b6cb-e603a8ccd39f";
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("email", guestEmail);
        placeholders.put("event", event);
        placeholders.put("organizerName", organizerName);
        placeholders.put("organizerSurname", organizerSurname);
        placeholders.put("invitationUrl", invitationUrl);
        String htmlContent = loadTemplate(templatePath, placeholders);
        return sendEmail(email, "Event invitation", htmlContent);
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

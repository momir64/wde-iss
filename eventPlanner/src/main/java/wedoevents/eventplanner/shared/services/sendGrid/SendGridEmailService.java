package wedoevents.eventplanner.shared.services.sendGrid;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendGridEmailService {
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

    public String sendVerificationEmail(String email, String name, String surname) throws IOException {
        String templatePath = "src/main/resources/templates/VerificationView.html";
        String verificationUrl = "https://yourdomain.com/verify?email=" + email; // Generate unique link5

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("email", email);
        placeholders.put("name", name);
        placeholders.put("surname", surname);
        placeholders.put("verificationUrl", verificationUrl);

        String htmlContent = loadTemplate(templatePath, placeholders);
        return sendEmail(email, "Verify Your Registration", htmlContent); // Sending email as HTML
    }


    public String loadTemplate(String templatePath, Map<String, String> placeholders) throws IOException {
        String template = new String(Files.readAllBytes(Paths.get(templatePath)));
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }
}

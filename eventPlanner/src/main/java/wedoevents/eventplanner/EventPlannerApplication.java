package wedoevents.eventplanner;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class EventPlannerApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(EventPlannerApplication.class, args);

        FileInputStream firebaseKey = new FileInputStream("../firebase-key.json");
        FirebaseApp.initializeApp(FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(firebaseKey)).build());
    }
}

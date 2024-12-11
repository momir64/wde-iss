package wedoevents.eventplanner.shared.services.imageService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    private final String imagesPath = "images";

    public String saveImageToStorage(MultipartFile imageFile, ImageLocationConfiguration locationConfiguration) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString();

        Path uploadPath = Path.of(imagesPath,locationConfiguration.contentType, locationConfiguration.contentUUID.toString());
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    public Optional<byte[]> getImage(String imageName, ImageLocationConfiguration locationConfiguration) throws IOException {
        Path imagePath = Path.of(imagesPath, locationConfiguration.contentType, locationConfiguration.contentUUID.toString(), imageName);

        if (Files.exists(imagePath)) {
            return Optional.of(Files.readAllBytes(imagePath));
        } else {
            return Optional.empty();
        }
    }

    public String deleteImage(String imageName) throws IOException {
        Path imagePath = Path.of(imagesPath, imageName);

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
            return "Success";
        } else {
            return "Failed";
        }
    }
}

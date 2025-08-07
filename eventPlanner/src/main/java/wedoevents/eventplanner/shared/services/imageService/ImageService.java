package wedoevents.eventplanner.shared.services.imageService;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    private final String imagesPath = "images";

    public String saveImageToStorage(MultipartFile imageFile, ImageLocationConfiguration locationConfiguration) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString();

        Path uploadPath;

        if (locationConfiguration.version != null) {
            uploadPath = Path.of(imagesPath, locationConfiguration.contentType, locationConfiguration.contentUUID.toString(), locationConfiguration.version.toString());
        }
        else {
            uploadPath = Path.of(imagesPath, locationConfiguration.contentType, locationConfiguration.contentUUID.toString());
        }
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    public Optional<byte[]> getImage(String imageName, ImageLocationConfiguration locationConfiguration) throws IOException {
        Path path = Path.of(imagesPath, locationConfiguration.contentType, locationConfiguration.contentUUID.toString());
        if (locationConfiguration.version != null)
            path = Path.of(path.toString(), locationConfiguration.version.toString(), imageName);
        else
            path = Path.of(path.toString(), imageName);

        if (Files.exists(path)) {
            return Optional.of(Files.readAllBytes(path));
        } else {
            return Optional.empty();
        }
    }

    public List<String> copyImagesToNewVersion(ImageLocationConfiguration locationConfiguration) throws IOException {
        Path oldImagesLocation = Path.of(imagesPath, locationConfiguration.contentType, locationConfiguration.contentUUID.toString(), locationConfiguration.version.toString());
        Path newImagesLocation = Path.of(imagesPath, locationConfiguration.contentType, locationConfiguration.contentUUID.toString(), String.valueOf(locationConfiguration.version + 1));

        List<String> copiedImages = new ArrayList<>();

        FileSystemUtils.copyRecursively(oldImagesLocation, newImagesLocation);

        for (File img : new File(newImagesLocation.toString()).listFiles()) {
            copiedImages.add(img.getName());
        }

        return copiedImages;
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

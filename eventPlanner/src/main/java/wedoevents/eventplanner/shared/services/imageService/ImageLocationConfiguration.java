package wedoevents.eventplanner.shared.services.imageService;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ImageLocationConfiguration {
    public String contentType;
    public UUID contentUUID;
    public Integer version;

    public ImageLocationConfiguration(String contentType, UUID contentUUID) {
        this.contentType = contentType;
        this.contentUUID = contentUUID;
        this.version = null;
    }
}

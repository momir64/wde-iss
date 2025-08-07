package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue
    private UUID Id;

    @OneToMany
    @JoinColumn(name = "chat")
    private List<ChatMessage> chatMessages;

    @ManyToOne
    @JoinColumn(name = "chatter1_profile_id", nullable = false)
    private Profile chatter1;

    @ManyToOne
    @JoinColumn(name = "chatter2_profile_id", nullable = false)
    private Profile chatter2;

    // one of these three will always be non-null

    @ManyToOne()
    private VersionedService service;
    @ManyToOne()
    private VersionedProduct product;
    @ManyToOne()
    private Event event;

    public Chat(Event event, VersionedProduct versionedProduct, VersionedService versionedService, Profile chatter1, Profile chatter2) {
        this.event = event;
        this.service = versionedService;
        this.product = versionedProduct;
        this.chatter1 = chatter1;
        this.chatter2 = chatter2;
        this.chatMessages = new ArrayList<>();
    }
}

package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    // one of these two will always be non-null

    @ManyToOne()
    private VersionedService service;
    @ManyToOne()
    private VersionedProduct product;
}

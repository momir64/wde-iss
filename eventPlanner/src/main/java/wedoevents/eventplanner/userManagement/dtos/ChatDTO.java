package wedoevents.eventplanner.userManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    private UUID chatId;
    private String lastMessage;
    private String listingName;
    private UUID listingId;
    private boolean seen;
    private String chatPartnerNameAndSurname;
    private UUID chatPartnerId;
    private LocalDateTime lastMessageDate;
    private ListingType listingType;
}

package wedoevents.eventplanner.userManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.userManagement.dtos.*;
import wedoevents.eventplanner.userManagement.models.*;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.repositories.ChatMessageRepository;
import wedoevents.eventplanner.userManagement.repositories.ChatRepository;
import wedoevents.eventplanner.userManagement.repositories.ProfileRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.GuestRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.SellerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final EventOrganizerRepository eventOrganizerRepository;
    private final SellerRepository sellerRepository;
    private final GuestRepository guestRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, ChatMessageRepository chatMessageRepository, EventOrganizerRepository eventOrganizerRepository, SellerRepository sellerRepository, GuestRepository guestRepository, ProfileRepository profileRepository) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.sellerRepository = sellerRepository;
        this.guestRepository = guestRepository;
        this.profileRepository = profileRepository;
    }

    public List<ChatDTO> getChatsFromProfile(UUID profileId) {
        List<Chat> chatsFromProfile = chatRepository.findChatsFromProfile(profileId);

        return chatsFromProfile
                .stream()
                .map(c -> this.getChatDTO(c, profileId)).toList();
    }

    public List<ChatMessageDTO> getChatMessagesFromChat(UUID chatId) {
        List<ChatMessage> chatMessages = chatMessageRepository.getAllChatMessagesFromChat(chatId);

        return chatMessages
                .stream()
                .map(cm -> new ChatMessageDTO(
                        cm.getMessage(),
                        cm.getTo().getId()
                )).toList();
    }

    public ChatDTO getChat(UUID profileId, UUID chatId) {
        Optional<Chat> chatMaybe = chatRepository.findById(chatId);

        if (chatMaybe.isEmpty()) {
            throw new NotImplementedException();
        }

        return getChatDTO(chatMaybe.get(), profileId);
    }

    private ChatDTO getChatDTO(Chat c, UUID profileId) {
        ChatMessage lastMessage = chatMessageRepository.getLastMessageFromChat(c.getId());

        UUID listingId;
        ListingType listingType;
        String listingName;
        if (c.getService() != null) {
            listingId = c.getService().getStaticServiceId();
            listingName = c.getService().getName();
            listingType = ListingType.SERVICE;
        } else {
            listingId = c.getProduct().getStaticProductId();
            listingName = c.getProduct().getName();
            listingType = ListingType.PRODUCT;
        }

        UUID chatPartnerId;
        if (c.getChatter1().getId().equals(profileId)) {
            chatPartnerId = c.getChatter2().getId();
        } else {
            chatPartnerId = c.getChatter1().getId();
        }

        String chatPartnerNameAndSurname = "";
        if (guestRepository.findByProfileId(chatPartnerId).isPresent()) {
            Guest guest = guestRepository.findByProfileId(chatPartnerId).get();
            chatPartnerNameAndSurname = guest.getName() + " " + guest.getSurname();
        } else if (eventOrganizerRepository.findByProfileId(chatPartnerId).isPresent()) {
            EventOrganizer eventOrganizer = eventOrganizerRepository.findByProfileId(chatPartnerId).get();
            chatPartnerNameAndSurname = eventOrganizer.getName() + " " + eventOrganizer.getSurname();
        } else if (sellerRepository.findByProfileId(chatPartnerId).isPresent()) {
            Seller seller = sellerRepository.findByProfileId(chatPartnerId).get();
            chatPartnerNameAndSurname = seller.getName() + " " + seller.getSurname();
        }

        return new ChatDTO(
                c.getId(),
                lastMessage.getMessage(),
                listingName,
                listingId,
                lastMessage.getIsSeen(),
                chatPartnerNameAndSurname,
                chatPartnerId,
                lastMessage.getTime(),
                listingType
        );
    }

    public ChatMessageDTO createMessage(NewChatMessageDTO newMessage) {
        Optional<Profile> recipientMaybe = profileRepository.findById(newMessage.getToProfileId());

        if (recipientMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Profile recipient = recipientMaybe.get();

        Optional<Chat> chatMaybe = chatRepository.findById(newMessage.getChatId());

        if (chatMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Chat chat = chatMaybe.get();

        ChatMessage newChatMessage = new ChatMessage(
                LocalDateTime.now(),
                newMessage.getMessage(),
                false,
                recipient
        );

        chatMessageRepository.addMessageToChat(
                newChatMessage.getIsSeen(),
                newChatMessage.getMessage(),
                newChatMessage.getTime(),
                newChatMessage.getTo().getId(),
                chat.getId()
        );

        return ChatMessageDTO.toDto(newChatMessage);
    }
}
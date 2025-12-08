package com.nocountry.crm.integration.whatsapp.service;

import com.nocountry.crm.dto.FileAttachment.FileAttachmentResponseDTO;
import com.nocountry.crm.entity.FileAttachment;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.integration.whatsapp.WhatsAppApiClient;
import com.nocountry.crm.integration.whatsapp.entity.Conversation;
import com.nocountry.crm.integration.whatsapp.dto.WhatsAppSendMessageDto;
import com.nocountry.crm.integration.whatsapp.entity.MessageSource;
import com.nocountry.crm.integration.whatsapp.entity.WhatsAppMessage;
import com.nocountry.crm.integration.whatsapp.repository.IWhatsAppMessageRepository;
import com.nocountry.crm.service.IFileAttachmentService;
import com.nocountry.crm.service.UserService;
import com.nocountry.crm.service.impl.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WhatsAppMessageService {

    private final WhatsAppApiClient apiClient;
    private final UserService userService;
    private final IConversationService conversationService;
    private final IWhatsAppMessageRepository repository;
    private final CloudinaryService cloudinaryService;
    private final IFileAttachmentService fileAttachmentService;

    public WhatsAppMessage saveMessage(WhatsAppSendMessageDto dto, Conversation conversation, FileAttachment attachment, String externalId) {

        User user = userService.findById(dto.userId());

        WhatsAppMessage message = new WhatsAppMessage();
        message.setConversation(conversation);
        //message.setContact(conversation.getContact());
        message.setUser(user);
        message.setContent(dto.content());
        message.setAttachment(attachment);
        message.setDateAndTime(LocalDateTime.now());
        message.setSource(MessageSource.USER);
        message.setExternalId(externalId);
        message.setCompany(user.getCompany());

        return repository.save(message);
    }

    public WhatsAppMessage sendTextMessage(WhatsAppSendMessageDto dto) {

        Conversation conversation = conversationService.getConversationByContactId(dto.contactId());

        String phone = conversation.getContact().getPhone();
        String externalMessageId = apiClient.sendMessage(phone, dto.content(), "text");

        conversation.setLastActivity(LocalDateTime.now());
        return saveMessage(dto, conversation, null, externalMessageId);
    }

    public WhatsAppMessage sendMediaMessage(WhatsAppSendMessageDto dto, MultipartFile file) throws IOException {

        String externalMediaId = apiClient.uploadMedia(file);

        FileAttachmentResponseDTO attachmentResponseDTO = cloudinaryService.uploadImage(file);
        FileAttachment fileAttachment = fileAttachmentService.getAttachmentByExternalId(attachmentResponseDTO.getId());

        String mediaType = resolveMediaType(file.getContentType());

        Conversation conversation = conversationService.getConversationByContactId(dto.contactId());

        String phone = conversation.getContact().getPhone();
        String externalMessageId = apiClient.sendMessage(phone, externalMediaId, mediaType);

        conversation.setLastActivity(LocalDateTime.now());
        return saveMessage(dto, conversation, fileAttachment, externalMessageId);
    }

    private String resolveMediaType(String type) {
        if (type.startsWith("image")) return "image";
        else if (type.startsWith("video")) return  "video";
        else if (type.startsWith("audio")) return  "audio";
        else return "document";
    }
}

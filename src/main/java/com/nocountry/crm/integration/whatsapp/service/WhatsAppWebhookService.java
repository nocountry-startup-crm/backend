package com.nocountry.crm.integration.whatsapp.service;

import com.nocountry.crm.entity.FileAttachment;
import com.nocountry.crm.integration.whatsapp.WhatsAppApiClient;
import com.nocountry.crm.integration.whatsapp.dto.WhatsAppWebhookPayload;
import com.nocountry.crm.integration.whatsapp.entity.Conversation;
import com.nocountry.crm.integration.whatsapp.entity.WhatsAppMessage;
import com.nocountry.crm.integration.whatsapp.mapper.WhatsAppMessageMapper;
import com.nocountry.crm.integration.whatsapp.repository.IWhatsAppMessageRepository;
import com.nocountry.crm.repository.ICompanyRepository;
import com.nocountry.crm.service.IFileAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WhatsAppWebhookService {

    private final WhatsAppApiClient apiClient;
    private final IWhatsAppMessageRepository repository;
    private final WhatsAppMessageMapper mapper;
    private final ConversationService conversationService;
    private final IFileAttachmentService fileAttachmentService;
    private final ICompanyRepository companyRepository;

    public WhatsAppMessage saveWhatsAppMessage(WhatsAppWebhookPayload.Message whatsappMessage, Conversation conversation, FileAttachment fileAttachment) {

        WhatsAppMessage message = mapper.toEntity(whatsappMessage);
        message.setConversation(conversation);
        message.setAttachment(fileAttachment);
        //message.setUser(null);
        message.setCompany(companyRepository.findByCode("777").get()); // get company by whatsappPhoneId

        return repository.save(message);
    }

    public WhatsAppMessage processTextMessageWebhook(WhatsAppWebhookPayload.Message message) {

        String phone = message.from();
        Conversation conversation = conversationService.getConversationByContactPhone(phone);
        conversation.setLastActivity(LocalDateTime.now());

        return saveWhatsAppMessage(message, conversation, null);
    }

    public WhatsAppMessage processMediaMessageWebhook(WhatsAppWebhookPayload.Message message, String mediaId, String filename, String mimeType) {

        byte[] fileBytes = apiClient.downloadMedia(mediaId);
        FileAttachment fileAttachment = fileAttachmentService.saveAttachment(fileBytes, filename, mimeType);

        String phone = message.from();
        Conversation conversation = conversationService.getConversationByContactPhone(phone);
        conversation.setLastActivity(LocalDateTime.now());

        return saveWhatsAppMessage(message, conversation, fileAttachment);

        /*// Guardar en disco
        File folder = new File("/opt/app/media");
        if (!folder.exists()) folder.mkdirs();
        File file = new File(folder, mediaId + ".bin");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileBytes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        /*String path = "downloads/" + (filename != null ? filename : mediaId);
        try {
            Files.write(Paths.get(path), fileBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }*/
    }
}

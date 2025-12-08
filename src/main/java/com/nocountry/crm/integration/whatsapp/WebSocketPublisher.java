package com.nocountry.crm.integration.whatsapp;

import com.nocountry.crm.integration.whatsapp.entity.WhatsAppMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publishNewTextMessage(WhatsAppMessage message) {

        String destination = "/topic/messages/"; // + message.getConversation().getId(); // companyCode?

        var payload = Map.of(
                "id", message.getId(),
                "conversationId", message.getConversation().getId(),
                "contactId", message.getConversation().getContact().getId(),
                "dateAndTime", message.getDateAndTime(),
                "content", message.getContent(),
                "source", message.getSource(),
                "externalMessageId", message.getExternalId()
                // companyCode?
        );
        messagingTemplate.convertAndSend(destination, payload);
    }

    public void publishNewMediaMessage(WhatsAppMessage message) {

        String destination = "/topic/files/"; // + message.getConversation().getId(); // companyCode?

        var payload = Map.of(
                "id", message.getId(),
                "conversationId", message.getConversation().getId(),
                "contactId", message.getConversation().getContact().getId(),
                "dateAndTime", message.getDateAndTime(),
                "content", message.getContent(),
                "attachmentUrl", message.getAttachment().getFileUrl(),
                "attachmentFileName", message.getAttachment().getFilename(),
                "source", message.getSource(),
                "externalMessageId", message.getExternalId()
                // companyCode?
        );
        messagingTemplate.convertAndSend(destination, payload);
    }
}

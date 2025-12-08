package com.nocountry.crm.integration.whatsapp.mapper;

import com.nocountry.crm.integration.whatsapp.dto.WhatsAppWebhookPayload;
import com.nocountry.crm.integration.whatsapp.entity.MessageSource;
import com.nocountry.crm.integration.whatsapp.entity.WhatsAppMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WhatsAppMessageMapper {

    public WhatsAppMessage toEntity(WhatsAppWebhookPayload.Message message) {
        WhatsAppMessage entity = new WhatsAppMessage();

        String externalMessageId = message.id();

        LocalDateTime timestamp = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(Long.parseLong(message.timestamp())),
                ZoneOffset.UTC
        );

        if (Objects.equals(message.type(), "text")) {
            entity.setContent(message.text().body());
        } else {
            entity.setContent(externalMessageId); //
        }
        entity.setDateAndTime(timestamp);
        entity.setSource(MessageSource.CONTACT);
        entity.setExternalId(externalMessageId);

        return entity;
    }
}

package com.nocountry.crm.integration.email.mapper;

import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.nocountry.crm.dto.response.ResponseEmailMessageDto;
import com.nocountry.crm.entity.EmailMessage;
import com.nocountry.crm.entity.enums.MessageSource;
import com.nocountry.crm.service.impl.ContactService;
import com.nocountry.crm.service.impl.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class GmailMessageMapper {
    private final ContactService contactService;
    private final ConversationService conversationService;

    public EmailMessage toEntity(Message gmailMessage) {
        EmailMessage entity = new EmailMessage();

        entity.setSnippet(gmailMessage.getSnippet());
        entity.setHistoryId(gmailMessage.getHistoryId());
        entity.setSource(MessageSource.CONTACT);

        for(MessagePartHeader header : gmailMessage.getPayload().getHeaders()) {
            switch (header.getName()) {
                case "From":
                    String value = header.getValue();
                    int start = value.indexOf('<');
                    int end = value.indexOf('>');
                    entity.setContact(contactService.findContactByEmail(value.substring(start+1, end)));
                    break;
                case "Subject":
                    entity.setSubject(header.getValue());
                    break;
                case "Date":
                    entity.setDateAndTime(parseGmailDate(header.getValue()));
                    break;
            }
        }

        entity.setConversation(conversationService
                .getConversationByContactId(entity.getContact().getId()));
        entity.setCompany(entity.getContact().getCompany());
        entity.setContent(getBody(gmailMessage.getPayload()));

        return entity;
    }

    public ResponseEmailMessageDto toDto(EmailMessage entity) {
        return new ResponseEmailMessageDto(
                entity.getId(),
                entity.getConversation().getId(), //
                entity.getContact().getId(),
                entity.getUser().getId(), //
                entity.getContent(),
                entity.getAttachment().getFileUrl(), //
                entity.getSource(), //
                entity.getSubject(),
                entity.getSnippet(),
                entity.getHistoryId(),
                entity.getRepliedEmailId(), //
                entity.getDateAndTime() //

        );
    }

    private LocalDateTime parseGmailDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        return ZonedDateTime.parse(dateString, formatter).toLocalDateTime();
    }

    private static String getBody(MessagePart part) {
        if (part == null)
            return "No message found";

        // If this part has data (base64 encoded)
        if (part.getBody() != null && part.getBody().getData() != null) {
            byte[] bytes = java.util.Base64.getUrlDecoder().decode(part.getBody().getData());
            return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        }

        // Otherwise check its parts recursively
        if (part.getParts() != null) {
            StringBuilder builder = new StringBuilder();
            for (MessagePart subPart : part.getParts()) {
                builder.append(getBody(subPart));
            }
            return builder.toString();
        }

        return "No message found";
    }
}

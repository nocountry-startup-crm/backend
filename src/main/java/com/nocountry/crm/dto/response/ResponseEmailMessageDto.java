package com.nocountry.crm.dto.response;

import com.nocountry.crm.entity.enums.MessageSource;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseEmailMessageDto(
        UUID id,
        UUID conversationId,
        UUID contactId,
        UUID userId,
        String content,
        String attachmentLink,
        MessageSource source,
        String subject,
        String snippet,
        BigInteger historyId,
        UUID repliedEmailId,
        LocalDateTime dateAndTime

)
{}

package com.nocountry.crm.integration.whatsapp.dto;

import java.util.UUID;

public record WhatsAppSendMessageDto(
        UUID userId, //
        UUID contactId,
        String content
) {}
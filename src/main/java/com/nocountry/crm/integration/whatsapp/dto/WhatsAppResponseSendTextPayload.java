package com.nocountry.crm.integration.whatsapp.dto;

import java.util.List;

public record WhatsAppResponseSendTextPayload(
    String messaging_product,
    List<Contacts> contacts,
    List<Messages> messages
) {
    public record Contacts(
            String input,
            String wa_id
    ) {}

    public record Messages(
            String id
    ) {}
}

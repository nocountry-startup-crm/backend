package com.nocountry.crm.integration.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WhatsAppWebhookPayload(
        //String object,
        List<Entry> entry
) {
    public record Entry(
            //String id,
            List<Change> changes
    ) {}

    public record Change(
            //String field,
            Value value
    ) {}

    public record Value(
            /*Metadata metadata,
            @JsonProperty("messaging_product")
            String messagingProduct,
            List<Contact> contacts,*/
            List<Message> messages
    ) {}

    /*public record Metadata(
            String phone_number_id
    ) {}

    public record Contact(
            @JsonProperty("wa_id")
            String waId,
            Profile profile
    ) {}

    public record Profile(
            String name
    ) {}*/

    public record Message(
            String from,
            String id,
            String timestamp,
            String type,

            Text text,
            Image image,
            Video video,
            Audio audio,
            Document document
    ) {}

    public record Text(
            String body
    ) {}

    public record Image(
            @JsonProperty("mime_type")
            String mimeType,
            String sha256,
            String id
    ) {}

    public record Video (
            @JsonProperty("mime_type")
            String mimeType,
            String sha256,
            String id
    ) {}

    public record Audio(
            @JsonProperty("mime_type")
            String mimeType,
            String sha256,
            String id,
            Boolean voice
    ) {}

    public record Document(
            String filename,
            @JsonProperty("mime_type")
            String mimeType,
            String sha256,
            String id
    ) {}
}
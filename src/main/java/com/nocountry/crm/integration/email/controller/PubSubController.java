package com.nocountry.crm.integration.email.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nocountry.crm.dto.request.RequestPubSubNotification;
import com.nocountry.crm.dto.response.ResponseEmailMessageDto;
import com.nocountry.crm.integration.email.service.GmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PubSubController {
    private final GmailService gmailService;

    @PostMapping("/pubsub/push")
    public ResponseEntity<List<ResponseEmailMessageDto>> receive(@RequestBody RequestPubSubNotification notification) {
        String base64Data = notification.message.data;

        String decodedJson = new String(Base64.getDecoder().decode(base64Data));

        JsonNode node;
        try {
            node = new ObjectMapper().readTree(decodedJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        long historyId = node.get("historyId").asLong();

        System.out.println("Received historyId = " + historyId);

        List<ResponseEmailMessageDto> dtos;
        try {
            dtos = gmailService.saveMessagesUsingHistoryId(historyId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/pubsub/push/{id}")
    public ResponseEntity<String> receive(@PathVariable("id") long historyId) {
        System.out.println("Received historyId = " + historyId);

        try {
            gmailService.saveMessagesUsingHistoryId(historyId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("ok");
    }

    @PostMapping("/pubsub/watch")
    public ResponseEntity<String> startGmailService() {
        try {
            gmailService.init();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing gmail service. " + e);
        }

        return ResponseEntity.ok("ok");
    }
}

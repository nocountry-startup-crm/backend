package com.nocountry.crm.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nocountry.crm.dto.request.RequestPubSubNotification;
import com.nocountry.crm.integration.email.GmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class PubSubController {
    private final GmailService gmailService;

    @PostMapping("/pubsub/push")
    public ResponseEntity<String> receive(@RequestBody RequestPubSubNotification notification) {
        String base64Data = notification.message.data;

        String decodedJson = new String(Base64.getDecoder().decode(base64Data));

        JsonNode node = new ObjectMapper().readTree(decodedJson);
        long historyId = node.get("historyId").asLong();

        System.out.println("Received historyId = " + historyId);

        try {
            gmailService.printMessagesUsingHistoryId(BigInteger.valueOf(historyId-1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("ok");
    }
}

package com.nocountry.crm.integration.whatsapp.controller;

import com.nocountry.crm.integration.whatsapp.dto.WhatsAppWebhookPayload;
import com.nocountry.crm.integration.whatsapp.entity.WhatsAppMessage;
import com.nocountry.crm.integration.whatsapp.WebSocketPublisher;
import com.nocountry.crm.integration.whatsapp.service.WhatsAppWebhookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/whatsapp")
public class WhatsAppWebhookController {

    private final WhatsAppWebhookService whatsAppWebhookService;
    private final WebSocketPublisher wsPublisher;

    @Value("${whatsapp.webhook.verify-token}")
    private String verifyToken;

    public WhatsAppWebhookController(WhatsAppWebhookService whatsAppWebhookService, WebSocketPublisher wsPublisher) {
        this.whatsAppWebhookService = whatsAppWebhookService;
        this.wsPublisher = wsPublisher;
    }

    @GetMapping
    public ResponseEntity<String> verify(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge
    ) {
        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(403).body("Invalid token");
    }

    @PostMapping
    public ResponseEntity<Void> receive(@RequestBody WhatsAppWebhookPayload payload) {

        payload.entry().forEach(entry ->
                entry.changes().forEach(change -> {
                    var value = change.value();
                    if (value.messages() == null) return;

                    value.messages().forEach(msg -> {

                        if ("text".equals(msg.type())) {
                            WhatsAppMessage whatsAppMessage = whatsAppWebhookService.processTextMessageWebhook(msg);
                            wsPublisher.publishNewTextMessage(whatsAppMessage);
                        }

                        if ("document".equals(msg.type())) {
                            WhatsAppMessage whatsAppMessage = whatsAppWebhookService.processMediaMessageWebhook(
                                    msg, msg.document().id(), msg.document().filename(), msg.document().mimeType());
                            wsPublisher.publishNewMediaMessage(whatsAppMessage);
                        }

                        if ("image".equals(msg.type())) {
                            WhatsAppMessage whatsAppMessage =whatsAppWebhookService.processMediaMessageWebhook(
                                    msg, msg.image().id(), msg.image().id(), msg.image().mimeType());
                            wsPublisher.publishNewMediaMessage(whatsAppMessage);
                        }

                        if ("audio".equals(msg.type())) {
                            WhatsAppMessage whatsAppMessage =whatsAppWebhookService.processMediaMessageWebhook(
                                    msg, msg.audio().id(), msg.audio().id(), msg.audio().mimeType());
                            wsPublisher.publishNewMediaMessage(whatsAppMessage);
                        }

                        if ("video".equals(msg.type())) {
                            WhatsAppMessage whatsAppMessage =whatsAppWebhookService.processMediaMessageWebhook(
                                    msg, msg.video().id(), msg.video().id(), msg.video().mimeType());
                            wsPublisher.publishNewMediaMessage(whatsAppMessage);
                        }
                    });
                })
        );

        return ResponseEntity.ok().build();
    }
}

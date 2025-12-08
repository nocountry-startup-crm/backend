package com.nocountry.crm.integration.whatsapp.controller;

import com.nocountry.crm.integration.whatsapp.dto.WhatsAppSendMessageDto;
import com.nocountry.crm.integration.whatsapp.entity.WhatsAppMessage;
import com.nocountry.crm.integration.whatsapp.WebSocketPublisher;
import com.nocountry.crm.integration.whatsapp.service.WhatsAppMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class WhatsAppWebsocketController {

    private final WhatsAppMessageService whatsAppMessageService;
    private final WebSocketPublisher wsPublisher;

    @MessageMapping("/sendMessage")
    public void send(@RequestBody WhatsAppSendMessageDto dto) {
        WhatsAppMessage whatsAppMessage = whatsAppMessageService.sendTextMessage(dto);
        wsPublisher.publishNewTextMessage(whatsAppMessage);
    }
}

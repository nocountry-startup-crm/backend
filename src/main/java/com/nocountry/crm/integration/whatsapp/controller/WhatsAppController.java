package com.nocountry.crm.integration.whatsapp.controller;

import com.nocountry.crm.dto.response.ResponseDto;
import com.nocountry.crm.integration.whatsapp.dto.WhatsAppSendMessageDto;
import com.nocountry.crm.integration.whatsapp.entity.WhatsAppMessage;
import com.nocountry.crm.integration.whatsapp.WebSocketPublisher;
import com.nocountry.crm.integration.whatsapp.service.WhatsAppMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.nocountry.crm.common.ApiPaths.WHATSAPP_BASE;

@RestController
@RequestMapping(WHATSAPP_BASE)
@RequiredArgsConstructor
public class WhatsAppController {

    private final WhatsAppMessageService whatsAppMessageService;
    private final WebSocketPublisher wsPublisher;

    @PostMapping(value = "/send-media")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER_ADMIN')")
    public ResponseDto<Void> sendMedia(
                                                        @RequestPart("dto") WhatsAppSendMessageDto dto,
                                                        @RequestPart(value = "file") MultipartFile file
    ) throws IOException {
        WhatsAppMessage whatsAppMessage = whatsAppMessageService.sendMediaMessage(dto, file);
        wsPublisher.publishNewMediaMessage(whatsAppMessage);
        return new ResponseDto<>(null, HttpStatus.OK, 0);
    }
}
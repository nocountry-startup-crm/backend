package com.nocountry.crm.integration.whatsapp;

import com.nocountry.crm.integration.whatsapp.dto.WhatsAppResponseSendTextPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class WhatsAppApiClient {

    @Value("${whatsapp.company.phone-id}")
    private String phoneId;

    @Value("${whatsapp.company.access-token}")
    private String accessToken;

    @Value("${whatsapp.api.base-url}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate;

    public WhatsAppApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendMessage(String phone, String content, String mediaType) {

        String url = apiBaseUrl + "/" + phoneId + "/messages";

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", phone,
                "type", mediaType,
                mediaType, Map.of(mediaType.equals("text") ? "body" : "id", content)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> req = new HttpEntity<>(body, headers);
        ResponseEntity<WhatsAppResponseSendTextPayload> res = restTemplate.postForEntity(
                url, req, WhatsAppResponseSendTextPayload.class
        );

        if (res.getStatusCode().is2xxSuccessful()) {
            System.out.println("Mensaje enviado correctamente");
            System.out.println("WhatsApp API Response: " + res.getBody().toString());
            return res.getBody().messages().get(0).id();
        } else {
            System.err.println("Error enviando mensaje: " + res.getStatusCode());
            throw new RuntimeException("Error enviando mensaje: " + res.getStatusCode());
        }
    }

    public String uploadMedia(MultipartFile file) throws IOException {

        String url = apiBaseUrl + "/" + phoneId + "/media";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("messaging_product", "whatsapp");
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
        body.add("type", file.getContentType()); // MIME (image/png, etc)

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        return response.getBody().get("id").toString(); // media_id
    }

    public byte[] downloadMedia(String mediaId) {

        String metadataUrl = apiBaseUrl + "/" + mediaId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        ResponseEntity<Map> response = restTemplate.exchange(
                metadataUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        String mediaUrl = (String) response.getBody().get("url"); //

        ResponseEntity<byte[]> media = restTemplate.exchange(
                mediaUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                byte[].class
        );

        return media.getBody();
    }
}

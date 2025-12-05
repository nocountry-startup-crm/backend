package com.nocountry.crm.integration.email.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import com.nocountry.crm.dto.response.ResponseEmailMessageDto;
import com.nocountry.crm.entity.EmailMessage;
import com.nocountry.crm.integration.email.mapper.GmailMessageMapper;
import com.nocountry.crm.repository.EmailMessageRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GmailService {
    public static final String TEST_EMAIL = "nccrm3326@gmail.com";
    private final HistoryTrackerService trackerService;
    private final GmailMessageMapper messageMapper;
    private final EmailMessageRepository emailMessageRepository;
    private Gmail service;

    private static final Set<String> scopes = new HashSet<>(List.of(
            GmailScopes.GMAIL_READONLY,
            GmailScopes.GMAIL_COMPOSE,
            GmailScopes.GMAIL_MODIFY,
            GmailScopes.GMAIL_LABELS));

    @PostConstruct
    public void init() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        this.service = new Gmail.Builder(
                httpTransport,
                jsonFactory,
                getCredentials(httpTransport, jsonFactory)
        )
                .setApplicationName("Test Mailer")
                .build();

        setUpWatch();
    }

    private void setUpWatch() throws IOException {
        WatchRequest watchRequest = new WatchRequest()
                .setLabelIds(List.of("INBOX"))
                .setTopicName("projects/nc-crm-479822/topics/DemoTopic");

        WatchResponse watchResponse = service.users().watch("me", watchRequest).execute();
        System.out.println("Gmail watch started. Expiration: " + watchResponse.getExpiration());
        System.out.println("Initial historyId: " + watchResponse.getHistoryId());

        trackerService.saveInitialHistoryId(watchResponse.getHistoryId());
    }

    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
            throws IOException {
        // Load client secrets.
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                new InputStreamReader(GmailService.class.getResourceAsStream("/client_secret.json")));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scopes) // what are we authorized to do?
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private void sendMail(String subject, String message) throws IOException, MessagingException {
        // Encode as MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(TEST_EMAIL));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(TEST_EMAIL));
        email.setSubject(subject);
        email.setText(message);

        // Encode and wrap the MIME message into a gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodedEmail);

        try {
            // Create send message
            msg = service.users().messages().send("me", msg).execute();
            System.out.println("Message id: " + msg.getId());
            System.out.println(msg.toPrettyString());
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public List<ResponseEmailMessageDto> saveMessagesUsingHistoryId(long newHistoryId) throws Exception {
        BigInteger previousHistoryId = trackerService.getLastHistoryId();
        BigInteger nextHistoryId = BigInteger.valueOf(newHistoryId);


        ListHistoryResponse historyResponse = service.users()
                .history()
                .list("me")
                .setStartHistoryId(previousHistoryId)
                .execute();

        List<String> newMessageIds = new ArrayList<>();

        if (historyResponse.getHistory() != null) {
            for (History history : historyResponse.getHistory()) {
                if (history.getMessagesAdded() != null) {
                    for (HistoryMessageAdded added : history.getMessagesAdded()) {
                        newMessageIds.add(added.getMessage().getId());
                    }
                }
            }
        }

        if (newMessageIds.isEmpty()) {
            trackerService.updateHistoryId(nextHistoryId);
            return List.of();
        }

        List<EmailMessage> saved = new ArrayList<>();

        for (String messageId : newMessageIds) {
            Message message = service.users()
                    .messages()
                    .get("me", messageId)
                    .setFormat("full")
                    .execute();
            EmailMessage savedMessage = emailMessageRepository.save(messageMapper.toEntity(message));
            saved.add(savedMessage);
        }

        trackerService.updateHistoryId(nextHistoryId);

        return saved
                .stream()
                .map(messageMapper::toDto)
                .toList();
    }



}

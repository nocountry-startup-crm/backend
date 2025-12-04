package com.nocountry.crm.integration.email;

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
import jakarta.annotation.PostConstruct;
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
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class GmailService {
    public static final String TEST_EMAIL = "nccrm3326@gmail.com";
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
                throw e;
            }
        }
    }

    private void printMessages(String label) throws Exception {
        // set label to "INBOX" or "SENT"
        ListMessagesResponse listResponse = service.users()
                .messages()
                .list("me")
                .setLabelIds(Collections.singletonList(label))
                .execute();

        List<Message> messages = listResponse.getMessages();

        if (messages == null || messages.isEmpty()) System.out.println("No messages found.");
        else {
            System.out.println("Messages: ");
            for (Message message : messages) {
                System.out.println("Message ID: " + message.getId());
                Message fullMessage = service.users()
                        .messages()
                        .get("me", message.getId())
                        .setFormat("full")
                        .execute();

                System.out.println("Snippet: " + fullMessage.getSnippet());

                for(MessagePartHeader header : fullMessage.getPayload().getHeaders()) {
                    if(header.getName().equals("From")) System.out.println(header.values());
                    if(header.getName().equals("To")) System.out.println(header.values());
                    if(header.getName().equals("Subject")) System.out.println(header.values());
                    if(header.getName().equals("Date")) System.out.println(header.values());
                }


                System.out.println(getBody(fullMessage.getPayload()));
                System.out.println();
            }
        }
    }

    public void printMessagesUsingHistoryId(BigInteger historyId) throws Exception {
        ListHistoryResponse historyResponse = service.users()
                .history()
                .list("me")
                .setStartHistoryId(historyId)
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

        if (newMessageIds.isEmpty()) System.out.println("No messages found.");
        else {
            System.out.println("Messages: ");
            for (String messageId : newMessageIds) {
                Message message = service.users()
                        .messages()
                        .get("me", messageId)
                        .setFormat("full")
                        .execute();

                System.out.println("Message ID: " + message.getId());
                System.out.println("History ID: " + message.getHistoryId());

                System.out.println("Snippet: " + message.getSnippet());

                for(MessagePartHeader header : message.getPayload().getHeaders()) {
                    if(header.getName().equals("From")) System.out.println(header.values());
                    if(header.getName().equals("To")) System.out.println(header.values());
                    if(header.getName().equals("Subject")) System.out.println(header.values());
                    if(header.getName().equals("Date")) System.out.println(header.values());
                }


                System.out.println(getBody(message.getPayload()));
                System.out.println();
            }
        }
    }

    private static String getBody(MessagePart part) {
        if (part == null)
            return "No message found";

        // If this part has data (base64 encoded)
        if (part.getBody() != null && part.getBody().getData() != null) {
            byte[] bytes = java.util.Base64.getUrlDecoder().decode(part.getBody().getData());
            return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        }

        // Otherwise check its parts recursively
        if (part.getParts() != null) {
            StringBuilder builder = new StringBuilder();
            for (MessagePart subPart : part.getParts()) {
                builder.append(getBody(subPart));
            }
            return builder.toString();
        }

        return "No message found";
    }

}

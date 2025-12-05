package com.nocountry.crm.service.impl;

import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.entity.Conversation;
import com.nocountry.crm.repository.ConversationRepository;
import com.nocountry.crm.service.IConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService implements IConversationService {
    private final ConversationRepository repository;
    private final ContactService contactService;

    @Override
    public Conversation getConversationByContactId(UUID contactId) {
        return repository.findByContact_Id(contactId)
                .orElseGet(() -> createNewConversation(contactId));
    }

    private Conversation createNewConversation(UUID contactId) {
        Conversation conversation = new Conversation();

        Contact contact = contactService.findById(contactId);

        conversation.setContact(contact);
        conversation.setLastActivity(LocalDateTime.now());

        return repository.save(conversation);
    }
}

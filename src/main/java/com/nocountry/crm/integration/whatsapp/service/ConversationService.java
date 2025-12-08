package com.nocountry.crm.integration.whatsapp.service;

import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.integration.whatsapp.entity.Conversation;
import com.nocountry.crm.integration.whatsapp.repository.IConversationRepository;
import com.nocountry.crm.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService implements IConversationService {
    private final IConversationRepository repository;
    private final IContactService contactService;

    @Override
    public Conversation getConversationByContactPhone(String phone) {
        Contact contact = contactService.getContactByPhone(phone);
        return repository.findByContact_Id(contact.getId())
                .orElseGet(() -> createNewConversation(contact));
    }

    @Override
    public Conversation getConversationByContactId(UUID contactId) {
        Contact contact = contactService.findById(contactId);
        return repository.findByContact_Id(contactId)
                .orElseGet(() -> createNewConversation(contact));
    }

    private Conversation createNewConversation(Contact contact) {

        Conversation conversation = new Conversation();
        conversation.setContact(contact);
        conversation.setLastActivity(LocalDateTime.now());

        return repository.save(conversation);
    }
}

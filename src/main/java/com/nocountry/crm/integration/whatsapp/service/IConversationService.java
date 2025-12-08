package com.nocountry.crm.integration.whatsapp.service;

import com.nocountry.crm.integration.whatsapp.entity.Conversation;

import java.util.UUID;

public interface IConversationService {
    Conversation getConversationByContactId(UUID contactId);
    Conversation getConversationByContactPhone(String phone);
}

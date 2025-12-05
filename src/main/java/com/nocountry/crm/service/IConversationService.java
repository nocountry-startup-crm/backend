package com.nocountry.crm.service;

import com.nocountry.crm.entity.Conversation;

import java.util.UUID;

public interface IConversationService {
    Conversation getConversationByContactId(UUID contactId);
}

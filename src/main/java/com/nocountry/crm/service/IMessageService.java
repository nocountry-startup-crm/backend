package com.nocountry.crm.service;

import com.nocountry.crm.entity.base.Message;

import java.util.List;
import java.util.UUID;

public interface IMessageService {
    List<Message> getAllMessagesWithContact(UUID contactId);


}

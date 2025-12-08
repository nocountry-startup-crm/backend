package com.nocountry.crm.integration.whatsapp.repository;

import com.nocountry.crm.integration.whatsapp.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IConversationRepository extends JpaRepository<Conversation, UUID> {
    Optional<Conversation> findByContact_Id(UUID contactId);
}

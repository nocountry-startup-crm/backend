package com.nocountry.crm.repository;

import com.nocountry.crm.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    Optional<Conversation> findByContact_Id(UUID contactId);
}

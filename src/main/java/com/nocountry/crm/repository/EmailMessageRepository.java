package com.nocountry.crm.repository;

import com.nocountry.crm.entity.EmailMessage;
import com.nocountry.crm.entity.base.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailMessageRepository extends JpaRepository<EmailMessage, UUID> {
}

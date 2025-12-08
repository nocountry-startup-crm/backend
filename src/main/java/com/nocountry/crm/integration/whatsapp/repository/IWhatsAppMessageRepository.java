package com.nocountry.crm.integration.whatsapp.repository;

import com.nocountry.crm.integration.whatsapp.entity.WhatsAppMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IWhatsAppMessageRepository extends JpaRepository<WhatsAppMessage, UUID> {
}

package com.nocountry.crm.integration.whatsapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "whatsapp_mesagges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppMessage extends Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    String externalId;
}

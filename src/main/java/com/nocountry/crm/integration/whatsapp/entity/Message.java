package com.nocountry.crm.integration.whatsapp.entity;

import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.entity.FileAttachment;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.entity.base.CompanyEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Message extends CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "contact_id")
    //private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    private FileAttachment attachment;

    private LocalDateTime dateAndTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageSource source;
}

/*
@SuperBuilder
@MappedSuperclass
@Data
public class Message extends CompanyEntity { // abstract
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}*/

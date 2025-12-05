package com.nocountry.crm.entity.base;

import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.entity.Conversation;
import com.nocountry.crm.entity.FileAttachment;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.entity.enums.MessageSource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Message extends CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

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

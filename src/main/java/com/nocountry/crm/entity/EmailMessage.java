package com.nocountry.crm.entity;

import com.nocountry.crm.entity.base.Message;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EmailMessage extends Message {
    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String snippet;

    @Column(nullable = false)
    private BigInteger historyId;

    @Column(name = "replied_email_id", nullable = true)
    private UUID repliedEmailId;
}

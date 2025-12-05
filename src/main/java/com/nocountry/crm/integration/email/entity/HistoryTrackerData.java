package com.nocountry.crm.integration.email.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "gmail_sync_state")
@Getter
@Setter
@NoArgsConstructor
public class HistoryTrackerData {
    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private BigInteger historyId;

}

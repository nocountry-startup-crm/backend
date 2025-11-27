package com.nocountry.crm.entity;

import com.nocountry.crm.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
public class Country extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String acronym;

    @Column(nullable = false, unique = true)
    private String phonePrefix;
}

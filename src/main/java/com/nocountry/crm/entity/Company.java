package com.nocountry.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nocountry.crm.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    private String logoUrl;

    //@Enumerated(EnumType.STRING)
    private String timeZone; // ENUM

    private String tokenDuration;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Set<User> userSet;
}

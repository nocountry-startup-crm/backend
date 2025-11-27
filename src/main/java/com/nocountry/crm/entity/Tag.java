package com.nocountry.crm.entity;

import com.nocountry.crm.entity.base.CompanyEntity;
import com.nocountry.crm.entity.enums.TagColor;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "tags")
@ToString
public class Tag extends CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    private TagColor color;

    @ManyToMany(mappedBy = "tags")
    private Set<Contact> contacts;

    //private Set<Filter> filters;
}

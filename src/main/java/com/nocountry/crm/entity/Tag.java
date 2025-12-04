package com.nocountry.crm.entity;

import com.nocountry.crm.entity.base.CompanyEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Tag extends CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "Name is required")
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)//
    @NotNull(message = "Color is required")//
    private String color;

    //@ManyToMany(mappedBy = "tags")
    //private Set<Contact> contacts;

    //private Set<Filter> filters;
}

package com.nocountry.crm.entity;

import com.nocountry.crm.entity.base.CompanyEntity;
import com.nocountry.crm.entity.enums.RoleCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends CompanyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String imageUrl;
    private String fullName;
    private String email;
    private String password;
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;
    @Enumerated(EnumType.STRING)
    private RoleCode role;
}
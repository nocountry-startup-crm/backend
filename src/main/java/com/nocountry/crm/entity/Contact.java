package com.nocountry.crm.entity;

import com.nocountry.crm.entity.base.CompanyEntity;
import com.nocountry.crm.entity.enums.FileType;
import com.nocountry.crm.entity.enums.FunnelStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", nullable = false, length = 255)
    @NotNull(message = "Full name is required")
    @Size(max = 255, message = "Full name cannot exceed 255 characters")
    private String fullName;

    @Column(name = "email", nullable = false, length = 255, unique = true)
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @Column(name = "phone", nullable = false, length = 15)
    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits")
    private String phone;

    @Column(name = "country_id")
    @Null
    private UUID countryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "funnel_status", nullable = false)
    @NotNull(message = "Funnel status is required")
    private FunnelStatus funnelStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @ManyToMany
    @JoinTable(
        name = "contact_tag",
        joinColumns = @JoinColumn(name = "contact_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}

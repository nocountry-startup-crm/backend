package com.nocountry.crm.entity;

import com.nocountry.crm.entity.enums.FileType;
import com.nocountry.crm.entity.enums.FunnelStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
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
}

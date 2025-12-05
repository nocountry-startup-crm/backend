package com.nocountry.crm.entity.base;

import com.nocountry.crm.entity.Company;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class CompanyEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}


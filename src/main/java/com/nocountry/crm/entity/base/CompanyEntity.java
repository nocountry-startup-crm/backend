package com.nocountry.crm.entity.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class CompanyEntity extends BaseEntity{
    public int companyId;
}

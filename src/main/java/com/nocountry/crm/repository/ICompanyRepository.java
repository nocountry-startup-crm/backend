package com.nocountry.crm.repository;

import com.nocountry.crm.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByCode(String code);
}

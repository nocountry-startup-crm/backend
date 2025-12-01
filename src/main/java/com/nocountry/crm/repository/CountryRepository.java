package com.nocountry.crm.repository;

import com.nocountry.crm.entity.Company;
import com.nocountry.crm.entity.Country;
import com.nocountry.crm.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> findByAcronym(String acronym);

}

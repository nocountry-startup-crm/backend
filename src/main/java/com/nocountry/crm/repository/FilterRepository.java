package com.nocountry.crm.repository;

import com.nocountry.crm.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FilterRepository extends JpaRepository<Filter, UUID> {
    Optional<Filter> findByCode(String code);

}

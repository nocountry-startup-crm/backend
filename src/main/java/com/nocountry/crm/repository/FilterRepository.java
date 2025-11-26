package com.nocountry.crm.repository;

import com.nocountry.crm.entity.Filter;
import com.nocountry.crm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilterRepository extends JpaRepository<Filter, UUID> {
    List<Filter> findByCompanyId(UUID companyId);
    Optional<Filter> findByCode(String code);
    boolean existsByName(String name);
    boolean existsByNameAndCompanyId(String name, UUID companyId);


}

package com.nocountry.crm.repository;

import com.nocountry.crm.entity.Filter;
import com.nocountry.crm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilterRepository extends JpaRepository<Filter, UUID> {
    List<Filter> findByCompanyId(UUID companyId);
    Optional<Filter> findByCode(String code);
    boolean existsByName(String name);
    boolean existsByNameAndCompanyId(String name, UUID companyId);


}

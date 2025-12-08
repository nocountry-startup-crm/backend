package com.nocountry.crm.repository;

import com.nocountry.crm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByCode(String code);
    boolean existsByName(String name);
}

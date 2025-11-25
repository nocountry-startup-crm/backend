package com.nocountry.crm.repository;

import com.nocountry.crm.entity.Tag;
import com.nocountry.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findByCreatedUserId(UUID createdUserId);
    boolean existsByNameAndCreatedUserId(String name, UUID createdUserId);
}

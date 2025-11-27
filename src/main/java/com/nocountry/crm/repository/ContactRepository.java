package com.nocountry.crm.repository;

import com.nocountry.crm.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID>, JpaSpecificationExecutor<Contact> {
    boolean existsByEmail(String email);
}

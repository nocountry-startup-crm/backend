package com.nocountry.crm.repository;

import com.nocountry.crm.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, UUID> {
    Optional<FileAttachment> findByExternalId(String externalId);
}

package com.nocountry.crm.repository;

import com.nocountry.crm.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, UUID> {

}

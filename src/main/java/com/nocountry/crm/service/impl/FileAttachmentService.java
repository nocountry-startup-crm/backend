package com.nocountry.crm.service.impl;

import com.nocountry.crm.entity.FileAttachment;
import com.nocountry.crm.repository.FileAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class FileAttachmentService {

    @Autowired
    private FileAttachmentRepository fileAttachmentRepository;

    public FileAttachment createAttachment(FileAttachment fileAttachment) {
        return fileAttachmentRepository.save(fileAttachment);
    }
}

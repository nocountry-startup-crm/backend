package com.nocountry.crm.service;

import com.nocountry.crm.entity.FileAttachment;

public interface IFileAttachmentService {
    FileAttachment save(FileAttachment fileAttachment);
    FileAttachment saveAttachment(byte[] fileBytes, String filename, String mimeType);
    FileAttachment getAttachmentByExternalId(String externalId);
}

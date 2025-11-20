package com.nocountry.crm.service;

import com.nocountry.crm.dto.FileAttachment.FileAttachmentResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService {
    FileAttachmentResponseDTO uploadImage(MultipartFile image) throws IOException;
}

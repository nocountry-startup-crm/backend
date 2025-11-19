package com.nocountry.crm.service.inter;

import com.nocountry.crm.dto.FileAttachment.FileAttachmentResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ImageService {
    FileAttachmentResponseDTO uploadImage(MultipartFile image) throws IOException;
}

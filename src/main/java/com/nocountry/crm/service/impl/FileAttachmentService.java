package com.nocountry.crm.service.impl;

import com.nocountry.crm.entity.FileAttachment;
import com.nocountry.crm.entity.enums.FileExtension;
import com.nocountry.crm.entity.enums.FileType;
import com.nocountry.crm.exception.FunctionalException;
import com.nocountry.crm.repository.FileAttachmentRepository;
import com.nocountry.crm.service.IFileAttachmentService;
import com.nocountry.crm.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FileAttachmentService implements IFileAttachmentService {

    @Autowired
    private FileAttachmentRepository fileAttachmentRepository;

    @Autowired
    private IImageService cloudinaryService;

    public FileAttachment createAttachment(FileAttachment fileAttachment) {
        return fileAttachmentRepository.save(fileAttachment);
    }

    @Override
    public FileAttachment save(FileAttachment fileAttachment) {
        return null;
    }

    @Override
    public FileAttachment saveAttachment(byte[] fileBytes, String filename, String mimeType) {
        Map<String, Object> uploadResponse = cloudinaryService.uploadMedia(fileBytes);

        String fileUrl = (String) uploadResponse.get("url");
        //String filename = image.getOriginalFilename();
        String externalId = (String) uploadResponse.get("public_id");

        String extension = mimeType.substring(mimeType.lastIndexOf('/') + 1).toUpperCase();
        FileType fileType = determineFileType(extension);
        FileExtension fileExtension = FileExtension.valueOf(extension);

        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setExternalId(externalId);
        fileAttachment.setFilename(filename);///
        fileAttachment.setFileUrl(fileUrl);
        fileAttachment.setType(fileType);
        fileAttachment.setExtension(fileExtension);

        return fileAttachmentRepository.save(fileAttachment);
    }

    @Override
    public FileAttachment getAttachmentByExternalId(String externalId) {
        return fileAttachmentRepository.findByExternalId(externalId)
                .orElseThrow(() -> new FunctionalException("Attachment not found.", HttpStatus.NOT_FOUND));
    }

    private FileType determineFileType(String extension) {
        return switch (extension) {
            case "JPG", "PNG", "JPEG" -> FileType.IMAGE;
            case "MP4" -> FileType.VIDEO;
            case "MP3" -> FileType.AUDIO;
            case "PDF" -> FileType.DOCUMENT;
            default -> FileType.DOCUMENT;
        };
    }
}

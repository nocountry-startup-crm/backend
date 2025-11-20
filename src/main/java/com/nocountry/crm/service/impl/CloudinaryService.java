package com.nocountry.crm.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nocountry.crm.entity.enums.FileExtension;
import com.nocountry.crm.entity.enums.FileType;
import com.nocountry.crm.dto.FileAttachment.FileAttachmentResponseDTO;
import com.nocountry.crm.entity.FileAttachment;
import com.nocountry.crm.repository.FileAttachmentRepository;
import com.nocountry.crm.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class CloudinaryService implements IImageService {
    private final Cloudinary cloudinary;
    private final FileAttachmentRepository fileAttachmentRepository;
    @Autowired
    public CloudinaryService(Cloudinary cloudinary, FileAttachmentRepository fileAttachmentRepository) {
        this.cloudinary = cloudinary;
        this.fileAttachmentRepository = fileAttachmentRepository;
    }

    public FileAttachmentResponseDTO uploadImage(MultipartFile image) throws IOException {
        FileAttachmentResponseDTO response = new FileAttachmentResponseDTO();

        Path tempDir = Files.createTempDirectory("upload_");
        Path tempFile = tempDir.resolve(image.getOriginalFilename());
        image.transferTo(tempFile.toFile());

        //File file = new File(image.getOriginalFilename());
        //image.transferTo(file);
        //FileOutputStream fo = new FileOutputStream(file);
        //fo.write(image.getBytes());
        //fo.close();

        Map<String, Object> params = ObjectUtils.asMap(
                "use_filename", false,
                "unique_filename", true,
                "overwrite", false
        );

        Map<String, Object> uploadResponse = cloudinary.uploader().upload(tempFile.toFile(), params);

        String fileUrl = (String) uploadResponse.get("url");
        String filename = image.getOriginalFilename();
        String externalId = (String) uploadResponse.get("public_id");

        String extension = filename.substring(filename.lastIndexOf('.') + 1).toUpperCase();
        FileType fileType = determineFileType(extension);
        FileExtension fileExtension = FileExtension.valueOf(extension);

        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setExternalId(externalId);
        fileAttachment.setFilename(filename);
        fileAttachment.setFileUrl(fileUrl);
        fileAttachment.setType(fileType);
        fileAttachment.setExtension(fileExtension);

        FileAttachment savedFileAttachment = fileAttachmentRepository.save(fileAttachment);

        response.setId(savedFileAttachment.getExternalId());
        response.setUrl(savedFileAttachment.getFileUrl());

        return response;
    }

    public Map<String, Object> deleteImage(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
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

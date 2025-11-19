package com.nocountry.crm.entity;

import com.nocountry.crm.common.FileExtension;
import com.nocountry.crm.common.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String externalId;
    private String filename;
    private String fileUrl;
    @Enumerated(EnumType.STRING)
    private FileType type;
    @Enumerated(EnumType.STRING)
    private FileExtension extension;
}

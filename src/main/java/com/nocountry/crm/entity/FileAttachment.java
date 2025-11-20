package com.nocountry.crm.entity;

import com.nocountry.crm.entity.base.BaseEntity;
import com.nocountry.crm.entity.enums.FileExtension;
import com.nocountry.crm.entity.enums.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "file_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileAttachment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String externalId;
    private String filename;
    private String fileUrl;
    @Enumerated(EnumType.STRING)
    private FileType type;
    @Enumerated(EnumType.STRING)
    private FileExtension extension;
}

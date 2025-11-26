package com.nocountry.crm.dto.response;

import com.nocountry.crm.entity.enums.RoleCode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ResponseUserDto(
        @Schema(description = "UUID del usuario", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID id,

        @Schema(
                description = "URL de la imagen del usuario",
                example = "https://www.pictures.com/3213124",
                required = true
        )
        String imageUrl,

        @Schema(
                description = "Nombre completo del usuario",
                example = "Juan Pérez",
                required = true
        )
        String fullName,

        @Schema(
                description = "Email del usuario",
                example = "juan@example.com",
                required = true
        )
        String email,

        @Schema(
                description = "Código de la empresa",
                example = "465FDF",
                required = true
        )
        String companyCode,

        @Schema(
                description = "Rol del usuario",
                example = "ADMIN",
                required = true
        )
        RoleCode role
) {
}

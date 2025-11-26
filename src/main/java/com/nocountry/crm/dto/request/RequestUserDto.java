package com.nocountry.crm.dto.request;

import com.nocountry.crm.entity.enums.RoleCode;
import io.swagger.v3.oas.annotations.media.Schema;

public record RequestUserDto(

        @Schema(
                description = "URL de la imagen del usuario",
                example = "https://www.pictures.com/3213124"
        )
        String imageUrl,

        @Schema(
                description = "Nombre completo del usuario",
                example = "Juan Pérez"
        )
        String fullName,

        @Schema(
                description = "Email del usuario",
                example = "juan@example.com"
        )
        String email,

        @Schema(
                description = "Contraseña del usuario",
                example = "password123"
        )
        String password,

        @Schema(
                description = "Código de la empresa",
                example = "465FDF"
        )
        String companyCode,

        @Schema(
                description = "Rol del usuario",
                example = "ADMIN"
        )
        RoleCode role
        )
{}

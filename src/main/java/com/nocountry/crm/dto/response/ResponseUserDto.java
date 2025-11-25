package com.nocountry.crm.dto.response;

import com.nocountry.crm.entity.enums.RoleCode;
import io.swagger.v3.oas.annotations.media.Schema;

public record ResponseUserDto(

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

package com.nocountry.crm.dto.request;


import com.nocountry.crm.entity.Country;
import com.nocountry.crm.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RequestFilterDto(
        @Schema(
                description = "Nombre del filtro",
                example = "Usuarios de América Latina"
        )
        String name,

        @Schema(
                description = "Código del filtro",
                example = "465FDF"
        )
        String code,

        @Schema(
                description = "Lista de códigos de tags",
                example = "[465FDF, 342GDF, 498GSD]"
        )
        List<String> tags,

        @Schema(
                description = "Lista de códigos de países",
                example = "[465FDF, 342GDF, 498GSD]"
        )
        List<String> countries,

        @Schema(
                description = "Fecha de creación del contacto (desde)",
                example = "2025-10-31T20:45:00"
        )
        LocalDateTime contactCreationFrom,

        @Schema(
                description = "Fecha de creación del contacto (hasta)",
                example = "2025-10-31T20:45:00"
        )
        LocalDateTime contactCreationTo
)
{}

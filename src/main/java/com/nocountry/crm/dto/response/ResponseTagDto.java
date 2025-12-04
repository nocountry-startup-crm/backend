package com.nocountry.crm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@Schema(description = "Response de CRUD de Tag")
public class ResponseTagDto {

    @Schema(description = "UUID del tag", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID id;

    @Schema(description = "Nombre del tag", example = "Llamada")
    private String name;

    @Schema(description = "Color en hexadecimal", example = "#FF5733")
    private String code;

    //@Schema(description = "Color del tag", example = "RED")
    private String color;
}

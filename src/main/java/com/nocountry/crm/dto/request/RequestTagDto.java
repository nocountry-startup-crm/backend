package com.nocountry.crm.dto.request;

import com.nocountry.crm.entity.enums.TagColor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Schema(description = "Request de CRUD de Tag")
public class RequestTagDto {

    @Schema(description = "Nombre del tag", example = "Llamada", required = true)
    @NotBlank(message = "El nombre del tag es obligatorio")
    private String name;

    @Schema(description = "Color en formato hexadecimal", example = "#FF5733")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "El color debe estar en formato hexadecimal (#RRGGBB)")
    private String code;

    @Schema(description = "Color del tag", example = "RED")
    @NotBlank(message = "Elegir un color para el tag es obligatorio")
    private TagColor color;
}

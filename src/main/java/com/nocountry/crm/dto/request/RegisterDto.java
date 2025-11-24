package com.nocountry.crm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request para registro de nuevo usuario")
public class RegisterDto {
    @Schema(
            description = "Nombre del usuario completo",
            example = "Juan Pérez",
            required = true
    )
    @NotBlank(message = "El nombre es obligatorio")
    String fullName;

    @Schema(
            description = "Email del usuario (debe ser único)",
            example = "juan@example.com",
            required = true,
            format = "email"
    )
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    String email;

    @Schema(
            description = "Contraseña del usuario",
            example = "password123",
            required = true
    )
    @NotBlank(message = "La contraseña es obligatoria")
    String password;

    @Schema(
            description = "Código de la empresa",
            example = "ADD545",
            required = true
    )
    @NotBlank(message = "El código de la empresa es obligatoria")
    String companyCode;
}

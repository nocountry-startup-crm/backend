package com.nocountry.crm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request para autenticación de usuario")
public class LoginDto {

    @Schema(
            description = "Email del usuario",
            example = "juan@example.com",
            required = true
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
            example = "465FDF",
            required = true
    )
    @NotBlank(message = "El código de la empresa es obligatoria")
    String companyCode;
}

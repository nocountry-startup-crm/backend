package com.nocountry.crm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@Schema(description = "Response de autenticación con tokens JWT")
public class AuthResponseDto {

    @Schema(
            description = "Token JWT",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNjE2MjM5MDIyfQ...",
            required = true
    )
    private String token;

    @Schema(
            description = "UUID del usuario",
            example = "5721554-d316-4c60-929c-6a5c0c501326",
            required = true
    )
    private UUID userId;
}

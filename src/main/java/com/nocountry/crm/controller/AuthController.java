package com.nocountry.crm.controller;

import com.nocountry.crm.dto.request.LoginDto;
import com.nocountry.crm.dto.request.RegisterDto;
import com.nocountry.crm.dto.response.AuthResponseDto;
import com.nocountry.crm.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nocountry.crm.common.ApiPaths.AUTH_BASE;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTH_BASE)
@Tag(name = "Authentication", description = "Endpoints de autenticación y registro de usuarios")
public class AuthController {
    private final IAuthService authService;

    @Operation(
            summary = "Registrar nuevo usuario hecho por otro usuario con permiso",
            description = "Crea una nueva cuenta de usuario (endpoint protegido) y retorna token JWT",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario registrado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "token": "eyJhbGciOiJIUzI1NiJ9...",
                                      "userId": "5721554-d316-4c60-929c-6a5c0c501326"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de registro inválidos o email ya existe",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "timestamp": "2025-11-24T10:30:00",
                                      "status": 400,
                                      "error": "Bad Request",
                                      "message": "El email ya está registrado"
                                    }
                                    """
                            )
                    )
            )
    })
    @PostMapping("/register")
    @PreAuthorize("hasRole('CUSTOMER_ADMIN')")
    public ResponseEntity<AuthResponseDto> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo usuario",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisterDto.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "fullName": "Juan Pérez",
                                      "email": "juan@example.com",
                                      "password": "password123",
                                      "companyCode: "465FDF"
                                    }
                                    """
                            )
                    )
            )
            Authentication authentication,
            @RequestBody RegisterDto registerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(authentication.getName(), registerDto));
    }

    @Operation(
            summary = "Iniciar sesión de usuario",
            description = "Autentica un usuario existente y retorna token JWT",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticación exitosa",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "token": "eyJhbGciOiJIUzI1NiJ9...",
                                      "userId": "5721554-d316-4c60-929c-6a5c0c501326"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciales inválidas",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "timestamp": "2025-11-24T10:30:00",
                                      "status": 401,
                                      "error": "Unauthorized",
                                      "message": "Credenciales inválidas"
                                    }
                                    """
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales de inicio de sesión",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginDto.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "email": "juan@example.com",
                                      "password": "password123",
                                      "companyCode": "465FDF"
                                    }
                                    """
                            )
                    )
            )
            @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}

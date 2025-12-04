package com.nocountry.crm.controller;

import com.nocountry.crm.dto.request.LoginDto;
import com.nocountry.crm.dto.request.RequestUserDto;
import com.nocountry.crm.dto.response.ResponseDto;
import com.nocountry.crm.dto.response.ResponseUserDto;
import com.nocountry.crm.security.UserPrincipal;
import com.nocountry.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.nocountry.crm.common.ApiPaths.USER_BASE;

@RequiredArgsConstructor
@RestController
@RequestMapping(USER_BASE)
@Tag(name = "CRUD user", description = "Endpoints de obtención, actualización y eliminación de usuarios")
public class UserController {
    private final UserService userService;

    // get user by id
    @Operation(
            summary = "Obtener usuario por UUID",
            description = "Obtiene los datos de un usuario por su UUID",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario obtenido exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseUserDto.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "imageUrl": "https://www.images.com/4564566",
                                      "fullName": "Juan Pérez",
                                      "email": "juan@email.com",
                                      "password": "$0fsf564-SFASF4,sadfaFDS2!FFFD",
                                      "companyCode": "ADF654",
                                      "roleCode": "ADMIN"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "timestamp": "2025-11-24T10:30:00",
                                      "status": 400,
                                      "error": "Bad Request",
                                      "message": "UUID del usuario no encontrado"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado - Requiere rol ADMIN",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "timestamp": "2025-11-24T10:30:00",
                                      "status": 403,
                                      "error": "Forbidden",
                                      "message": "Access Denied"
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_ADMIN')")
    public ResponseDto<ResponseUserDto> getUser(
            @Parameter(description = "UUID del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        ResponseUserDto user = userService.getUserById(id);

        return new ResponseDto<>(user, HttpStatus.OK, 0);
//        return ResponseEntity.ok(user);
    }

    // get all users
    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Obtiene todos los usuarios",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuarios obtenidos exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseUserDto.class),
                            examples = @ExampleObject(
                                    value = """
                                    [
                                        {
                                          "imageUrl": "https://www.images.com/4564566",
                                          "fullName": "Juan Pérez",
                                          "email": "juan@email.com",
                                          "password": "$0fsf564-SFASF4,sadfaFDS2!FFFD",
                                          "companyCode": "ADF654",
                                          "roleCode": "ADMIN"
                                        },
                                        {
                                          "imageUrl": "https://www.images.com/4487888",
                                          "fullName": "Ana Garcia",
                                          "email": "ana@email.com",
                                          "password": "$0fssd-Sd·$·,sadfaFDSdsFFFD",
                                          "companyCode": "ADF652",
                                          "roleCode": "USER"
                                        }
                                    ]
                                    """
                            )
                    )
            )
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_ADMIN')")
    public ResponseDto<List<ResponseUserDto>> getAllUsers() {
        return new ResponseDto<>(userService.getAllUsers(), HttpStatus.OK, 0);
//        return ResponseEntity.ok(userService.getAllUsers());
    }

    // update
    @Operation(
            summary = "Actualizar usuario por UUID",
            description = "Actualiza un usuario existente por su UUID",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseUserDto.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "imageUrl": "https://www.images.com/4564566",
                                      "fullName": "Juan Pérez Update",
                                      "email": "juan@email.com",
                                      "password": "$0fsf564-SFASF4,sadfaFDS2!FFFD",
                                      "companyCode": "ADF654",
                                      "roleCode": "ADMIN"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado"
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseDto<ResponseUserDto> updateUser(
            @Parameter(description = "UUID del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario para actualizar",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginDto.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "fullName": "Juan Pérez Updade"
                                    }
                                    """
                            )
                    )
            )
            @RequestPart("user") RequestUserDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        ResponseUserDto updatedUser = userService.updateUser(id, dto, image);
        return new ResponseDto<>(updatedUser, HttpStatus.OK, 0);
//        return ResponseEntity.ok(updatedUser);
    }

    // update
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER_ADMIN')")
    @PutMapping()
    public ResponseDto<ResponseUserDto> updateUser(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestPart("user") RequestUserDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        UUID id = user.getUser().getId();
        ResponseUserDto updatedUser = userService.updateUser(id, dto, image);

        return new ResponseDto<>(updatedUser, HttpStatus.OK, 0);
//        return ResponseEntity.ok(updatedUser);
    }

    // delete by id
    @Operation(
            summary = "Eliminar usuario por UUID",
            description = "Elimina un usuario por su UUID",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_ADMIN')")
    public ResponseDto<Void> deleteUser(
            @Parameter(description = "UUID del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        userService.deleteUser(id);
        return new ResponseDto<>(null, HttpStatus.OK, 0);
//        return ResponseEntity.noContent().build();
    }
}

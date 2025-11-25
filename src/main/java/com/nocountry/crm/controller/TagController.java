package com.nocountry.crm.controller;

import com.nocountry.crm.dto.request.LoginDto;
import com.nocountry.crm.dto.request.RequestTagDto;
import com.nocountry.crm.dto.response.ResponseTagDto;
import com.nocountry.crm.dto.response.ResponseUserDto;
import com.nocountry.crm.service.ITagService;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.nocountry.crm.common.ApiPaths.TAG_BASE;

@RequiredArgsConstructor
@RestController
@RequestMapping(TAG_BASE)
@Tag(name = "CRUD tag", description = "Endpoints de creación, obtención, actualización y eliminación de tags")
public class TagController {
    private final ITagService tagService;

    @Operation(
            summary = "Crear nuevo tag",
            description = "Crea un nuevo tag para filtrar tareas",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tag creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ResponseTagDto.class))
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER ADMIN')")
    public ResponseEntity<ResponseTagDto> createCategory(
            Authentication authentication,
            @RequestBody RequestTagDto request) {
        String userEmail = authentication.getName();
        ResponseTagDto response = tagService.createTag(userEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Obtener tag por UUID",
            description = "Obtiene un tag específico por su UUID",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER ADMIN')")
    public ResponseEntity<ResponseTagDto> getTagById(
            Authentication authentication,
            @Parameter(description = "UUID del tag", example = "550e8400-e29b-41d4-a716-446655440001")
            @PathVariable UUID id) {
        String userEmail = authentication.getName();
        ResponseTagDto tag = tagService.getTagById(userEmail, id);
        return ResponseEntity.ok(tag);
    }

    @Operation(
            summary = "Obtener todos los tags",
            description = "Obtiene todos los tags del usuario",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER ADMIN')")
    public ResponseEntity<List<ResponseTagDto>> getAllTagsByUser(
            Authentication authentication) {
        String userEmail = authentication.getName();
        List<ResponseTagDto> tags = tagService.getAllTagsByUser(userEmail);
        return ResponseEntity.ok(tags);
    }

    @Operation(
            summary = "Actualizar tag por UUID",
            description = "Actualiza un tag por su UUID",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tag actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = ResponseTagDto.class))
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER ADMIN')")
    public ResponseEntity<ResponseTagDto> updateTag(
            @Parameter(description = "UUID del tag", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del tag para actualizar",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RequestTagDto.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "name": "Llamada Update"
                                    }
                                    """
                            )
                    )
            )
            Authentication authentication,
            @RequestBody RequestTagDto request) {
        String userEmail = authentication.getName();
        ResponseTagDto response = tagService.updateTag(userEmail, id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Eliminar tag por UUID",
            description = "Elimina un tag por su UUID",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER ADMIN')")
    public ResponseEntity<Void> deleteTag(
            Authentication authentication,
            @Parameter(description = "UUID del tag", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        String userEmail = authentication.getName();
        tagService.deleteTag(userEmail, id);
        return ResponseEntity.noContent().build();
    }
}

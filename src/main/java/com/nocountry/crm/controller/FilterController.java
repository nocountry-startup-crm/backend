package com.nocountry.crm.controller;

import com.nocountry.crm.dto.request.RequestFilterDto;
import com.nocountry.crm.dto.request.RequestTagDto;
import com.nocountry.crm.dto.response.ResponseFilterDto;
import com.nocountry.crm.dto.response.ResponseTagDto;
import com.nocountry.crm.service.IFilterService;
import com.nocountry.crm.service.impl.FilterServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.nocountry.crm.common.ApiPaths.FILTER_BASE;
import static com.nocountry.crm.common.ApiPaths.USER_BASE;

@RequiredArgsConstructor
@RestController
@RequestMapping(FILTER_BASE)
public class FilterController {
    private final IFilterService filterService;

    @Operation(
            summary = "Crear nuevo filtro",
            description = "Crea un nuevo filtro que pueda ser utilizado por distintos usuarios",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Filtro creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ResponseFilterDto.class))
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER ADMIN')")
    public ResponseEntity<ResponseFilterDto> createTag(
            Authentication authentication,
            @RequestBody RequestTagDto request) {
        String userEmail = authentication.getName();
        ResponseFilterDto response = filterService.createFilter(userEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Obtener filtro por UUID",
            description = "Obtiene un filtro específico por su UUID",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER ADMIN')")
    public ResponseEntity<ResponseFilterDto> getFilterById(
            Authentication authentication,
            @Parameter(description = "UUID del filtro", example = "550e8400-e29b-41d4-a716-446655440001")
            @PathVariable UUID id) {
        String userEmail = authentication.getName();
        ResponseFilterDto tag = filterService.getFilterById(userEmail, id);
        return ResponseEntity.ok(tag);
    }

    @Operation(
            summary = "Obtener todos los filtros",
            description = "Obtiene todos los filtros del usuario",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER ADMIN')")
    public ResponseEntity<List<ResponseFilterDto>> getAllFiltersByUser(
            Authentication authentication) {
        String userEmail = authentication.getName();
        List<ResponseFilterDto> tags = filterService.getAllFiltersByUser(userEmail);
        return ResponseEntity.ok(tags);
    }

    @Operation(
            summary = "Actualizar filtro por UUID",
            description = "Actualiza un filtro por su UUID",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Filtro actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = ResponseFilterDto.class))
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CUSTOMER ADMIN')")
    public ResponseEntity<ResponseFilterDto> updateFilter(
            @Parameter(description = "UUID del filtro", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del filtro para actualizar",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RequestFilterDto.class),
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
        ResponseFilterDto response = filterService.updateFilter(userEmail, id, request);
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
            @Parameter(description = "UUID del filtro", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        String userEmail = authentication.getName();
        filterService.deleteFilter(userEmail, id);
        return ResponseEntity.noContent().build();
    }

}

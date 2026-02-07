package com.cpalacios.tenpo.app.controller;




import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.dto.TransaccionUpdateDTO;
import com.cpalacios.tenpo.app.service.TransaccionUpdateService;
import com.cpalacios.tenpo.app.swagger.schema.ErrorResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para actualizar transacciones.
 */
@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
@Tag(name = "Transacciones", description = "Endpoints para la gestión de transacciones financieras")
public class TransaccionUpdateController {

    private final TransaccionUpdateService service;

    /**
     * Endpoint para actualizar una transacción existente.
     *
     * @param dto DTO con los datos a actualizar
     * @return DTO de la transacción actualizada
     */
    @Operation(summary = "Actualizar una transacción", 
               description = "Actualiza los campos permitidos de una transacción existente, incluido el nombre del cliente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transacción actualizada exitosamente",
                content = @Content(schema = @Schema(implementation = TransaccionResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Error de validación o regla de negocio",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(
    	        responseCode = "500",
    	        description = "Error inesperado del servidor",
    	        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
    	    )
        
    })
    @PutMapping("/update")
    public ResponseEntity<TransaccionResponseDTO> actualizar(@Parameter(
            name = "X-Client-Id",
            description = "ID del cliente que se envía en el header (requerido para el rate limit)",
            required = true,
            in = ParameterIn.HEADER
    )
    @RequestHeader("X-Client-Id") String clientId,
            @Valid @RequestBody TransaccionUpdateDTO dto
    ) {
        return ResponseEntity.ok(service.actualizarTransaccion(dto));
    }
}

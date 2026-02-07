package com.cpalacios.tenpo.app.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpalacios.tenpo.app.dto.TransaccionRequestDTO;
import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.service.TransaccionService;
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
 * Controlador REST responsable de exponer los endpoints
 * relacionados con las transacciones.
 *
 * <p>
 * Esta clase actúa como punto de entrada de la API para operaciones
 * de creación de transacciones, delegando toda la lógica de negocio
 * al servicio correspondiente.
 * </p>
 *
 * <p>
 * El controlador se mantiene deliberadamente liviano, limitándose
 * a la validación de datos de entrada y al mapeo de respuestas HTTP,
 * cumpliendo con el principio de responsabilidad única (SRP).
 * </p>
 */
@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor

@Tag(
	    name = "Transacciones",
	    description = "Endpoints para la creación y gestión de transacciones financieras"
	)

public class TransaccionController {

    /**
     * Servicio encargado de manejar la lógica de negocio
     * relacionada con transacciones.
     */
    private final TransaccionService service;

    /**
     * Endpoint para crear una nueva transacción.
     *
     * <p>
     * Recibe los datos de la transacción a través de un DTO,
     * los valida automáticamente mediante {@code jakarta.validation}
     * y delega la creación al servicio de aplicación.
     * </p>
     *
     * <p>
     * En caso de éxito, retorna una respuesta HTTP 201 (Created)
     * con la información de la transacción creada.
     * </p>
     *
     * @param dto DTO que contiene los datos necesarios para crear la transacción
     * @return {@link ResponseEntity} con el {@link TransaccionResponseDTO} creado
     */
    
    @Operation(
    	    summary = "Crear una transacción",
    	    description = "Registra una nueva transacción financiera asociada a un cliente"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "201",
    	        description = "Transacción creada exitosamente",
    	        content = @Content(schema = @Schema(implementation = TransaccionResponseDTO.class))
    	    ),
    	    @ApiResponse(
    	        responseCode = "400",
    	        description = "Error de validación o regla de negocio",
    	        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
    	    ),
    	    @ApiResponse(
    	        responseCode = "500",
    	        description = "Error inesperado del servidor",
    	        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
    	    )
    	})
    @PostMapping("/create")
    public ResponseEntity<TransaccionResponseDTO> crear(@Parameter(
            name = "X-Client-Id",
            description = "ID del cliente que se envía en el header (requerido para el rate limit)",
            required = true,
            in = ParameterIn.HEADER
    )
    @RequestHeader("X-Client-Id") String clientId,
            @Valid @RequestBody TransaccionRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crearTransaccion(dto));
    }
}

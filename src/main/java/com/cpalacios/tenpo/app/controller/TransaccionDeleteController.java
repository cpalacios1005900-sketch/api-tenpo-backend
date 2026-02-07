package com.cpalacios.tenpo.app.controller;




import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.cpalacios.tenpo.app.service.TransaccionDeleteService;
import com.cpalacios.tenpo.app.swagger.schema.ErrorResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

public class TransaccionDeleteController {

    /**
     * Servicio encargado de manejar la lógica de negocio
     * relacionada con transacciones.
     */
    private final TransaccionDeleteService service;

    
    
    /**
     * Elimina una transacción por su identificador.
     *
     * @param id identificador de la transacción
     * @return respuesta HTTP 204 si se elimina correctamente
     */
    @Operation(
    	    summary = "Eliminar una transacción",
    	    description = "Elimina una transacción existente por su identificador"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "204",
    	        description = "Transacción eliminada correctamente"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "La transacción no existe",
    	        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
    	    ),
    	    @ApiResponse(
    	        responseCode = "500",
    	        description = "Error inesperado del servidor",
    	        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
    	    )
    	})
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> eliminar(@Parameter(
            name = "X-Client-Id",
            description = "ID del cliente que se envía en el header (requerido para el rate limit)",
            required = true,
            in = ParameterIn.HEADER
    )
    @RequestHeader("X-Client-Id") String clientId,
            @RequestParam(required = true) Long idTransaccion
    ) {

        return ResponseEntity.ok(Map.of("message", service.eliminarTransaccion(idTransaccion)));
    }
}

package com.cpalacios.tenpo.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.service.TransaccionQueryService;
import com.cpalacios.tenpo.app.swagger.schema.ErrorResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para consultas de transacciones.
 *
 * <p>
 * Expone endpoints para listar todas las transacciones
 * y para buscar transacciones usando un filtro único.
 * Documentado con Swagger/OpenAPI.
 * </p>
 */
@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
@Tag(
        name = "Transacciones",
        description = "Endpoints para consultar transacciones financieras"
)
public class TransaccionQueryController {

    private final TransaccionQueryService service;

    /**
     * Endpoint para obtener todas las transacciones registradas.
     *
     * @return lista de todas las transacciones
     */
    @Operation(
            summary = "Listar todas las transacciones",
            description = "Devuelve una lista completa de todas las transacciones registradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de transacciones",
            		content = @Content(
            	            array = @ArraySchema(schema = @Schema(implementation = TransaccionResponseDTO.class))
            	        )
    )
    @GetMapping("/all")
    public ResponseEntity<List<TransaccionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    /**
     * Endpoint para buscar transacciones usando un filtro.
     *
     * <p>
     * El parámetro {@code filtro} se aplica sobre múltiples campos
     * de la entidad (nombre del cliente, número de transacción, monto, giro, fecha)
     * y permite coincidencias parciales (LIKE). Es obligatorio.
     * </p>
     *
     * @param filtro valor de búsqueda (obligatorio)
     * @return lista de transacciones que coinciden con el filtro
     */
    @Operation(
    	    summary = "Buscar transacciones por filtro",
    	    description = "Permite buscar transacciones aplicando un filtro único sobre varios campos. El parámetro 'filtro' es obligatorio."
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Lista de transacciones que coinciden con el filtro",
    	        content = @Content(
    	            array = @ArraySchema(schema = @Schema(implementation = TransaccionResponseDTO.class))
    	        )
    	    ),
    	    @ApiResponse(
    	        responseCode = "400",
    	        description = "Error de validación: filtro nulo o vacío",
    	        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
    	    ),
    	    @ApiResponse(
    	        responseCode = "500",
    	        description = "Error inesperado del servidor",
    	        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
    	    )
    	})
    @GetMapping("/search")
    public ResponseEntity<List<TransaccionResponseDTO>> buscar(
    		@RequestParam(required = true) @NotBlank(message = "El filtro no puede ser vacío o nulo") String filtro
    ) {
        return ResponseEntity.ok(service.buscarPorFiltro(filtro));
    }
}

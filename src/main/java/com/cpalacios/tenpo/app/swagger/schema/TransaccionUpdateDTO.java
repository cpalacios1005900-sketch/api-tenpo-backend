package com.cpalacios.tenpo.app.swagger.schema;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para actualizar una transacción existente.
 */

@Schema(description = "DTO para actualizar una transacción existente")
public class TransaccionUpdateDTO {
	
	 @Schema(
		        description = "id de la transacción",
		        example = "1"
		    )
		    private Integer idTransaccion;
	 

	 @Schema(
		        description = "Nombre del cliente (tenpista)",
		        example = "Carlos Palacios"
		    )
		    private String nombreTenpista;

		    @Schema(
		        description = "Número único de la transacción",
		        example = "12"
		    )
		    private Integer numeroTransaccion;

		    @Schema(
		        description = "Monto de la transacción en pesos",
		        example = "25000"
		    )
		    private Integer montoPesos;

		    @Schema(
		        description = "Giro o comercio de la transacción",
		        example = "Supermercado"
		    )
		    private String giroComercio;

		    @Schema(
		        description = "Fecha y hora de la transacción",
		        example = "2026-02-05T10:30:00"
		    )
		    private LocalDateTime fechaTransaccion;
}

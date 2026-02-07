package com.cpalacios.tenpo.app.swagger.schema;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con la información de la transacción creada")
public class TransaccionResponseDTO {

    @Schema(example = "10")
    private Long idTransaccion;

    @Schema(example = "Carlos Palacios")
    private String nombreTenpista;

    @Schema(example = "25000")
    private Integer montoPesos;
    
    @Schema(example = "25000")
    private String giroComercio;

    @Schema(example = "2026-02-05T10:30:00")
    private LocalDateTime fechaTransaccion;
    
    
}

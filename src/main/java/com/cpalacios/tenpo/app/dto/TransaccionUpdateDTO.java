package com.cpalacios.tenpo.app.dto;


import java.time.LocalDateTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO para actualizar una transacción existente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionUpdateDTO {

    @NotNull(message = "El id de la transacción es obligatorio")
    private Long idTransaccion;

    @NotNull(message = "El número de la transacción es obligatorio")
    @Min(value = 1, message = "El número de la transacción debe ser mayor o igual a 1")
    private Integer numeroTransaccion;

    @NotNull(message = "El monto de la transacción es obligatorio")
    @Min(value = 1, message = "El monto de la transacción debe ser mayor o igual a 1")
    private Integer montoPesos;

    @NotBlank(message = "El giro o comercio es obligatorio")
    private String giroComercio;

    @NotNull(message = "La fecha de la transacción es obligatoria")
    private LocalDateTime fechaTransaccion;

    @NotBlank(message = "El nombre del tenpista es obligatorio")
    private String nombreTenpista;
}


package com.cpalacios.tenpo.app.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO de entrada para la creación de una transacción.
 *
 * <p>
 * Esta clase representa la información que el cliente de la API envía
 * para registrar una nueva transacción. No expone entidades JPA y se
 * utiliza exclusivamente en la capa de transporte (Controller).
 * </p>
 *
 * <p>
 * Incluye validaciones mediante Bean Validation (Jakarta Validation)
 * para garantizar que los datos recibidos cumplan con las reglas
 * mínimas antes de llegar a la capa de negocio.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionRequestDTO {

    /**
     * Nombre del cliente (tenpista) asociado a la transacción.
     *
     * <p>
     * Este campo es obligatorio y se utiliza como identificador lógico
     * del cliente. La validación de unicidad y existencia se realiza
     * en la capa de servicio.
     * </p>
     */
    @NotBlank(message = "El nombre del tenpista es obligatorio y no puede estar vacío")
    private String nombreTenpista;

    /**
     * Número de la transacción.
     *
     * <p>
     * Debe ser un valor positivo mayor o igual a 1.
     * </p>
     */
    @NotNull(message = "El número de la transacción es obligatorio")
    @Min(value = 1, message = "El número de la transacción debe ser mayor o igual a 1")
    private Integer numeroTransaccion;

    /**
     * Monto de la transacción expresado en pesos.
     *
     * <p>
     * El monto debe ser un valor positivo mayor o igual a 1.
     * </p>
     */
    @NotNull(message = "El monto de la transacción es obligatorio")
    @Min(value = 1, message = "El monto de la transacción debe ser mayor o igual a 1")
    private Integer montoPesos;

    /**
     * Giro o comercio donde se realizó la transacción.
     *
     * <p>
     * Este campo es obligatorio y describe el establecimiento
     * asociado a la transacción.
     * </p>
     */
    @NotBlank(message = "El giro o comercio es obligatorio y no puede estar vacío")
    private String giroComercio;

    /**
     * Fecha y hora en que se realizó la transacción.
     *
     * <p>
     * Debe ser proporcionada por el cliente de la API y no puede ser nula.
     * </p>
     */
    @NotNull(message = "La fecha de la transacción es obligatoria")
    private LocalDateTime fechaTransaccion;
}


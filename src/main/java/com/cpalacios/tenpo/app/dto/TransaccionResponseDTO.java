package com.cpalacios.tenpo.app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para operaciones relacionadas con transacciones.
 *
 * <p>
 * Esta clase representa la información que la API retorna al consumidor
 * luego de crear o consultar una transacción. Se utiliza exclusivamente
 * como objeto de transferencia de datos (DTO) y no contiene lógica
 * de negocio ni anotaciones de persistencia.
 * </p>
 *
 * <p>
 * Su propósito es exponer únicamente los datos necesarios para el cliente
 * de la API, evitando el acoplamiento con las entidades JPA internas
 * y protegiendo la integridad del modelo de dominio.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionResponseDTO {

    /**
     * Identificador único de la transacción.
     *
     * <p>
     * Este valor es generado por el sistema al persistir la transacción
     * y permite identificarla de forma única dentro de la plataforma.
     * </p>
     */
    private Long idTransaccion;
    
    /**
     * numeroTransaccion de la transacción.
     *
     * <p>
     * Este valor es generado por el sistema al persistir la transacción
     * y permite identificarla de forma única dentro de la plataforma.
     * </p>
     */
    private Integer numeroTransaccion;

    

    /**
     * Nombre del cliente (tenpista) asociado a la transacción.
     *
     * <p>
     * Se expone como referencia informativa 
     * de la transacción, sin revelar detalles internos de la entidad Cliente.
     * </p>
     */
    private String nombreTenpista;

    /**
     * Monto de la transacción expresado en pesos.
     *
     * <p>
     * Representa el valor económico de la transacción realizada.
     * </p>
     */
    private Integer montoPesos;

    /**
     * Fecha y hora en que se realizó la transacción.
     *
     * <p>
     * Se expone para fines informativos, auditoría o visualización
     * en el frontend.
     * </p>
     */
    private LocalDateTime fechaTransaccion;
    
    /**
     * Mensaje informativo sobre el resultado de la operación.
     */
    private String message;
    
    /**
     * Giro o comercio donde se realizó la transacción.
     *
     * <p>
     * describe el establecimientoasociado a la transacción.
     * </p>
     */
 
    private String giroComercio;
}

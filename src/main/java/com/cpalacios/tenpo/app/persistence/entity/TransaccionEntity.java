package com.cpalacios.tenpo.app.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa una transacción financiera.
 *
 * Cada transacción pertenece a un cliente.
 */
@Entity
@Table(
    name = "transacciones",
    indexes = {
        @Index(name = "idx_transacciones_cliente", columnList = "id_cliente")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionEntity {

    /**
     * Identificador único de la transacción.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long idTransaccion;

    /**
     * Número de transacción.
     */
    @Column(name = "numero_transaccion", nullable = false)
    private Integer numeroTransaccion;

    /**
     * Monto de la transacción en pesos.
     * Debe ser mayor que cero.
     */
    @Column(name = "monto_pesos", nullable = false)
    private Integer montoPesos;

    /**
     * Giro o comercio donde se realizó la transacción.
     */
    @Column(name = "giro_comercio", nullable = false, length = 100)
    private String giroComercio;

    /**
     * Fecha y hora de la transacción.
     */
    @Column(name = "fecha_transaccion", nullable = false)
    private LocalDateTime fechaTransaccion;

    /**
     * Cliente al que pertenece la transacción.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;
}

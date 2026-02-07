package com.cpalacios.tenpo.app.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa a un cliente del sistema.
 *
 * Un cliente puede tener como máximo 100 transacciones asociadas.
 */
@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteEntity {

    /**
     * Identificador único del cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    /**
     * Nombre del tenpista asociado al cliente.
     */
    @Column(name = "nombre_tenpista", nullable = false, length = 100)
    private String nombreTenpista;
}
package com.cpalacios.tenpo.app.mapper;

import org.springframework.stereotype.Component;

import com.cpalacios.tenpo.app.dto.TransaccionRequestDTO;
import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;
import com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity;

/**
 * Mapper responsable de convertir entre DTOs de transacción y la entidad
 * {@link TransaccionEntity}.
 *
 * <p>
 * Esta clase actúa como una capa intermedia entre la capa de transporte
 * (DTOs utilizados por los controladores) y la capa de persistencia
 * (entidades JPA), evitando el acoplamiento directo entre ambas.
 * </p>
 *
 * <p>
 * Su uso centraliza la lógica de conversión de datos y cumple con el
 * principio de responsabilidad única (SRP), facilitando el mantenimiento,
 * las pruebas unitarias y la evolución del modelo de datos.
 * </p>
 */
@Component
public class TransaccionMapper {

    /**
     * Convierte un {@link TransaccionRequestDTO} en una entidad
     * {@link TransaccionEntity}.
     *
     * <p>
     * Este método se utiliza principalmente al momento de crear una
     * nueva transacción a partir de los datos recibidos desde la API.
     * La asociación con el {@link ClienteEntity} se realiza explícitamente
     * para garantizar la integridad de la relación.
     * </p>
     *
     * @param dto     DTO que contiene los datos de entrada de la transacción
     * @param cliente entidad del cliente previamente validada o creada
     * @return entidad {@link TransaccionEntity} lista para ser persistida
     */
    public TransaccionEntity toEntity(
            TransaccionRequestDTO dto,
            ClienteEntity cliente
    ) {
        return TransaccionEntity.builder()
                .numeroTransaccion(dto.getNumeroTransaccion())
                .montoPesos(dto.getMontoPesos())
                .giroComercio(dto.getGiroComercio())
                .fechaTransaccion(dto.getFechaTransaccion())
                .cliente(cliente)
                .build();
    }

    /**
     * Convierte una entidad {@link TransaccionEntity} en un
     * {@link TransaccionResponseDTO}.
     *
     * <p>
     * Este método se utiliza para transformar la información persistida
     * en un objeto de respuesta que puede ser enviado al consumidor
     * de la API, exponiendo únicamente los datos necesarios.
     * </p>
     *
     * @param entity entidad {@link TransaccionEntity} obtenida desde la base de datos
     * @return DTO de respuesta {@link TransaccionResponseDTO}
     */
    public TransaccionResponseDTO toResponse(TransaccionEntity entity) {
        return TransaccionResponseDTO.builder()
                .idTransaccion(entity.getIdTransaccion())
                .numeroTransaccion(entity.getNumeroTransaccion())
                .nombreTenpista(entity.getCliente().getNombreTenpista())
                .giroComercio(entity.getGiroComercio())
                .montoPesos(entity.getMontoPesos())
                .fechaTransaccion(entity.getFechaTransaccion()).message("Transacción Exitosa")
                .build();
    }
}

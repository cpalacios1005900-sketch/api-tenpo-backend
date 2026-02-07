package com.cpalacios.tenpo.app.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpalacios.tenpo.app.dto.TransaccionUpdateDTO;
import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.exception.BusinessException;
import com.cpalacios.tenpo.app.mapper.TransaccionMapper;
import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;
import com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity;
import com.cpalacios.tenpo.app.persistence.repository.ClienteRepository;
import com.cpalacios.tenpo.app.persistence.repository.TransaccionRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la actualización de transacciones.
 *
 * <p>
 * Permite modificar campos de una transacción existente, validando
 * que la transacción exista y que el cliente asociado no se cree de nuevo.
 * </p>
 */
@Service
@RequiredArgsConstructor

public class TransaccionUpdateService {

    private final TransaccionRepository transaccionRepository;
    private final TransaccionMapper mapper;
    private final ClienteRepository clienteRepository;

    /**
     * Actualiza los datos de una transacción existente.
     *
     * @param dto DTO con los datos de actualización
     * @return DTO con la transacción actualizada
     */
    @Transactional
	public TransaccionResponseDTO actualizarTransaccion(TransaccionUpdateDTO dto) {

		try {
			TransaccionEntity transaccion = transaccionRepository.findById(dto.getIdTransaccion()).orElseThrow(
					() -> new BusinessException("No existe una transacción con id: " + dto.getIdTransaccion()));

			// Solo actualizar los campos permitidos
			transaccion.setNumeroTransaccion(dto.getNumeroTransaccion());
			transaccion.setMontoPesos(dto.getMontoPesos());
			transaccion.setGiroComercio(dto.getGiroComercio());
			transaccion.setFechaTransaccion(dto.getFechaTransaccion());
			
			// Manejo de cliente
	        String nuevoNombreCliente = dto.getNombreTenpista();

			// Actualizar el nombre del cliente existente
			transaccion.getCliente().setNombreTenpista(dto.getNombreTenpista());
			
			  // Verificar si ya existe un cliente con ese nombre
	        ClienteEntity clienteExistente = clienteRepository.findByNombreTenpistaIgnoreCase(nuevoNombreCliente)
	                .orElse(null);

	        if (clienteExistente != null) {
	            // Si existe, no se actualiza el cliente actual, se asocia el existente
	            transaccion.setCliente(clienteExistente);
	        } else {
	            // Si no existe, crear un nuevo cliente y asaociarlo
	            ClienteEntity nuevoCliente = ClienteEntity.builder()
	                    .nombreTenpista(nuevoNombreCliente)
	                    .build();
	            clienteRepository.save(nuevoCliente);

	            transaccion.setCliente(nuevoCliente);
	        }


			TransaccionEntity updated = transaccionRepository.save(transaccion);

			return mapper.toResponse(updated);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("La Transaccion '" + dto.getNumeroTransaccion() + "' no se logro actualizar");
		}
	}
}

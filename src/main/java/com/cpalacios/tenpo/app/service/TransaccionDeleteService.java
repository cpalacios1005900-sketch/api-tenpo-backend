
package com.cpalacios.tenpo.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpalacios.tenpo.app.exception.BusinessException;
import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;
import com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity;
import com.cpalacios.tenpo.app.persistence.repository.ClienteRepository;
import com.cpalacios.tenpo.app.persistence.repository.TransaccionRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la gestión de transacciones.
 *
 * <p>
 * Contiene la lógica de negocio relacionada con la creación,
 * validación y eliminación de transacciones.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TransaccionDeleteService {

    private final TransaccionRepository transaccionRepository;
    private final ClienteRepository clienteRepository;

    /**
     * Elimina una transacción por su identificador.
     *
     * <p>
     * Valida que la transacción exista antes de eliminarla.
     * Si no existe, se lanza una excepción de negocio.
     * </p>
     *
     * @param idTransaccion identificador de la transacción
     * @throws BusinessException si la transacción no existe
     */
    @Transactional
	public String eliminarTransaccion(Long idTransaccion) {
		try {
			TransaccionEntity transaccion = transaccionRepository.findById(idTransaccion)
					.orElseThrow(() -> new BusinessException("No existe una transacción con id: " + idTransaccion));

			transaccionRepository.delete(transaccion);

			ClienteEntity cliente = transaccion.getCliente();

			// Verificar si el cliente tiene otras transacciones
			long countTransacciones = transaccionRepository.countByCliente_IdCliente(cliente.getIdCliente());

			if (countTransacciones == 0) {
				// Si no tiene más transacciones, eliminar al cliente
				clienteRepository.delete(cliente);
			}

			return "La transacción con id " + idTransaccion + " fue eliminada correctamente";
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("La Transaccion no se logro eliminar");
		}
	}
}

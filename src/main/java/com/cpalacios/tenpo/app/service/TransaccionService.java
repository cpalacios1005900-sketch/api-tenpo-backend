package com.cpalacios.tenpo.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpalacios.tenpo.app.dto.TransaccionRequestDTO;
import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.exception.BusinessException;
import com.cpalacios.tenpo.app.mapper.TransaccionMapper;
import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;
import com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity;
import com.cpalacios.tenpo.app.persistence.repository.ClienteRepository;
import com.cpalacios.tenpo.app.persistence.repository.TransaccionRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de aplicación encargado de gestionar las operaciones relacionadas
 * con transacciones.
 *
 * <p>
 * Esta clase contiene la lógica de negocio principal para la creación de
 * transacciones, incluyendo la validación de reglas del dominio, la gestión de
 * clientes y la coordinación entre repositorios y mappers.
 * </p>
 *
 * <p>
 * Cumple con los principios SOLID, manteniendo responsabilidades claras:
 * <ul>
 * <li>Validar reglas de negocio</li>
 * <li>Orquestar el acceso a datos</li>
 * <li>Coordinar la conversión entre DTOs y entidades</li>
 * </ul>
 * </p>
 *
 * <p>
 * Todas las operaciones se ejecutan dentro de una transacción para garantizar
 * la consistencia de los datos.
 * </p>
 */
@Service
@RequiredArgsConstructor

public class TransaccionService {

	/**
	 * Límite máximo permitido de transacciones por cliente.
	 */
	private static final int MAX_TRANSACCIONES = 100;

	/**
	 * Repositorio para el acceso y gestión de clientes.
	 */
	private final ClienteRepository clienteRepository;

	/**
	 * Repositorio para el acceso y gestión de transacciones.
	 */
	private final TransaccionRepository transaccionRepository;

	/**
	 * Mapper encargado de convertir entre DTOs y entidades de transacción.
	 */
	private final TransaccionMapper mapper;

	/**
	 * Crea una nueva transacción aplicando las reglas de negocio definidas por el
	 * dominio.
	 *
	 * <p>
	 * Flujo del proceso:
	 * <ol>
	 * <li>Busca el cliente por nombre .</li>
	 * <li>Si el cliente no existe, lo crea.</li>
	 * <li>Valida que el cliente no supere el máximo de transacciones
	 * permitidas.</li>
	 * <li>Construye y persiste la transacción.</li>
	 * <li>Retorna la información de la transacción creada.</li>
	 * </ol>
	 * </p>
	 *
	 * @param dto objeto de entrada que contiene los datos de la transacción
	 * @return {@link TransaccionResponseDTO} con la información de la transacción
	 *         creada
	 *
	 * @throws BusinessException si el cliente supera el límite máximo de
	 *                           transacciones permitidas
	 */
	@Transactional
	public TransaccionResponseDTO crearTransaccion(TransaccionRequestDTO dto) {
		try {
			// Validar unicidad del número de transacción
			validarNumeroTransaccion(dto.getNumeroTransaccion());

			// ️ Validar máximo 100 transacciones por cliente
			long total = transaccionRepository.count();

			if (total >= MAX_TRANSACCIONES) {
				throw new BusinessException(
						"El cliente ya tiene el máximo de 100 transacciones");
			}
			ClienteEntity cliente = clienteRepository.findByNombreTenpistaIgnoreCase(dto.getNombreTenpista())
					.orElseGet(() -> crearCliente(dto.getNombreTenpista()));

			TransaccionEntity transaccion = mapper.toEntity(dto, cliente);

			TransaccionEntity saved = transaccionRepository.save(transaccion);
			return mapper.toResponse(saved);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("La Transaccion '" + dto.getNumeroTransaccion() + "' no se logro registrar");
		}
	}

	/**
	 * Crea y persiste un nuevo cliente en el sistema.
	 *
	 * <p>
	 * Este método se utiliza únicamente cuando no se encuentra un cliente existente
	 * con el nombre proporcionado. La lógica de validación de unicidad se maneja
	 * previamente en el servicio.
	 * </p>
	 *
	 * @param nombreTenpista nombre del cliente a crear
	 * @return {@link ClienteEntity} persistida
	 */
	private ClienteEntity crearCliente(String nombreTenpista) {
		ClienteEntity cliente = ClienteEntity.builder().nombreTenpista(nombreTenpista).build();

		return clienteRepository.save(cliente);
	}
	
    /**
     * Valida que el número de transacción no exista previamente.
     *
     * @param numeroTransaccion número de la transacción a validar
     * @throws BusinessException si el número de transacción ya existe
     */
    private void validarNumeroTransaccion(Integer numeroTransaccion) {
        if (transaccionRepository.existsByNumeroTransaccion(numeroTransaccion)) {
            throw new BusinessException(
                "Ya existe una transacción con el número: " + numeroTransaccion
            );
        }
    }
    
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
    public void eliminarTransaccion(Long idTransaccion) {

        TransaccionEntity transaccion = transaccionRepository
                .findById(idTransaccion)
                .orElseThrow(() ->
                        new BusinessException(
                                "No existe una transacción con id: " + idTransaccion
                        )
                );

        transaccionRepository.delete(transaccion);
    }
}

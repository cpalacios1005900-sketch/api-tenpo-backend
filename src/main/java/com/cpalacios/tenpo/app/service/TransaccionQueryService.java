package com.cpalacios.tenpo.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.exception.BusinessException;
import com.cpalacios.tenpo.app.mapper.TransaccionMapper;
import com.cpalacios.tenpo.app.persistence.repository.TransaccionRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de las operaciones de consulta de transacciones.
 *
 * <p>
 * Este servicio utiliza el método {@code buscarPorFiltro} del
 * {@link TransaccionRepository} para permitir búsquedas avanzadas sobre
 * múltiples campos de la entidad
 * {@link com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity}.
 * </p>
 *
 * <p>
 * La búsqueda es insensible a mayúsculas y minúsculas (LOWER) y permite
 * coincidencias parciales mediante LIKE.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransaccionQueryService {

	private final TransaccionRepository transaccionRepository;
	private final TransaccionMapper mapper;

	/**
	 * Retorna todas las transacciones existentes.
	 *
	 * @return lista de DTOs de todas las transacciones
	 */
	public List<TransaccionResponseDTO> listarTodas() {
		try {
			return transaccionRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
		} catch (Exception e) {
			throw new BusinessException("No se logro consultar las transacciones");
		}
	}

	/**
	 * Retorna las transacciones que coinciden con el filtro proporcionado.
	 *
	 * <p>
	 * El filtro se aplica sobre varios campos de la entidad, incluyendo:
	 * <ul>
	 * <li>Nombre del cliente</li>
	 * <li>Número de transacción</li>
	 * <li>Monto</li>
	 * <li>Giro/Comercio</li>
	 * <li>Fecha de transacción</li>
	 * </ul>
	 * </p>
	 *
	 * @param filtro texto de búsqueda que se compara con todos los campos
	 * @return lista de DTOs que cumplen con el filtro
	 */
	public List<TransaccionResponseDTO> buscarPorFiltro(String filtro) {
		try {
			return transaccionRepository.buscarPorFiltro(filtro).stream().map(mapper::toResponse)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new BusinessException("No se logro consultar las transacciones");
		}
	}
}

package com.cpalacios.tenpo.app.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity;

/**
 * Repositorio JPA para la entidad {@link TransaccionEntity}.
 *
 * <p>
 * Esta interfaz extiende {@link JpaRepository} y proporciona operaciones
 * estándar CRUD para la gestión de transacciones, así como métodos de
 * consulta derivados basados en convenciones de nombres de Spring Data JPA.
 * </p>
 *
 * <p>
 * Los métodos definidos aquí permiten realizar validaciones y consultas
 * relacionadas con las transacciones asociadas a un cliente, sin exponer
 * detalles de implementación ni lógica de negocio.
 * </p>
 *
 * <p>
 * Todas las consultas se generan automáticamente por Spring Data JPA,
 * manteniendo el repositorio enfocado exclusivamente en el acceso a datos
 * (principio de responsabilidad única).
 * </p>
 */
public interface TransaccionRepository extends JpaRepository<TransaccionEntity, Long> {

    

    /**
     * Método de consulta derivado
     * Verifica si ya existe una transacción con el número de transacción indicado.
     *
     * <p>
     * Este método se utiliza para aplicar una regla de negocio que impide
     * registrar transacciones duplicadas por número de transacción.
     * </p>
     *
     * @param numeroTransaccion número de la transacción a validar
     * @return {@code true} si ya existe una transacción con ese número,
     *         {@code false} en caso contrario
     */
    boolean existsByNumeroTransaccion(Integer numeroTransaccion);
    
    /**
     * Busca transacciones filtrando por cualquier campo relevante de la entidad.
     *
     * <p>
     * La búsqueda es insensible a mayúsculas/minúsculas (LOWER)
     * y aplica LIKE para permitir coincidencias parciales.
     * </p>
     *
     * @param filtro valor de búsqueda que se compara con varios campos
     * @return lista de transacciones que coinciden con el filtro
     */
    @Query("SELECT t FROM TransaccionEntity t " +
           "JOIN t.cliente c " +
           "WHERE LOWER(c.nombreTenpista) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
           "   OR CAST(t.numeroTransaccion AS string) LIKE CONCAT('%', :filtro, '%') " +
           "   OR CAST(t.montoPesos AS string) LIKE CONCAT('%', :filtro, '%') " +
           "   OR LOWER(t.giroComercio) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
           "   OR CAST(t.fechaTransaccion AS string) LIKE CONCAT('%', :filtro, '%')")
    List<TransaccionEntity> buscarPorFiltro(@Param("filtro") String filtro);
    
    /**
     * Método de consulta derivado
     * Cuenta cuántas transacciones existen para un cliente dado.
     *
     * @param idCliente ID del cliente
     * @return número de transacciones asociadas
     */
    long countByCliente_IdCliente(Long idCliente);

    
}

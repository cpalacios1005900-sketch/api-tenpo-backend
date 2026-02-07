package com.cpalacios.tenpo.app.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;

/**
 * Repositorio JPA para la entidad {@link ClienteEntity}.
 *
 * <p>
 * Esta interfaz extiende {@link JpaRepository} y proporciona operaciones
 * estándar CRUD para la gestión de clientes dentro del sistema.
 * </p>
 *
 * <p>
 * Además de las operaciones básicas, expone métodos de consulta derivados
 * que permiten buscar clientes utilizando criterios específicos, manteniendo
 * el repositorio enfocado exclusivamente en el acceso a datos y no en la
 * lógica de negocio.
 * </p>
 *
 * <p>
 * Las consultas se generan automáticamente mediante las convenciones de
 * nombres de Spring Data JPA, lo que favorece un código más limpio,
 * desacoplado y fácil de mantener.
 * </p>
 */
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    /**
     * Busca un cliente por su nombre, sin distinguir entre mayúsculas
     * y minúsculas.
     *
     * <p>
     * Este método es útil para identificar de forma única a un cliente
     * cuando el nombre funciona como clave lógica dentro del dominio,
     * evitando duplicados causados por diferencias de capitalización.
     * </p>
     *
     * <p>
     * Retorna un {@link Optional} para forzar al consumidor del método
     * a manejar explícitamente el caso en que el cliente no exista,
     * promoviendo un código más seguro y expresivo.
     * </p>
     *
     * @param nombreTenpista nombre del cliente utilizado como criterio de búsqueda
     *                       (la comparación es case-insensitive)
     * @return un {@link Optional} que contiene el cliente si existe,
     *         o vacío si no se encuentra
     */
    Optional<ClienteEntity> findByNombreTenpistaIgnoreCase(String nombreTenpista);
}

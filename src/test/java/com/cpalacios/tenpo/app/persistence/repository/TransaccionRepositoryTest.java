package com.cpalacios.tenpo.app.persistence.repository;


import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;
import com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransaccionRepositoryTest {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private ClienteEntity cliente;
    private TransaccionEntity transaccion;

    @BeforeEach
    void setUp() {
        transaccionRepository.deleteAll();
        clienteRepository.deleteAll();

        cliente = ClienteEntity.builder()
                .nombreTenpista("Juan Perez")
                .build();
        clienteRepository.save(cliente);

        transaccion = TransaccionEntity.builder()
                .numeroTransaccion(123)
                .montoPesos(1000)
                .giroComercio("Comercio 1")
                .fechaTransaccion(LocalDateTime.now())
                .cliente(cliente)
                .build();
        transaccionRepository.save(transaccion);
    }

    @Test
    void countByCliente_NombreTenpistaIgnoreCase_ShouldReturnCorrectCount() {
        long count = transaccionRepository.count();
        assertEquals(1, count);
    }

    @Test
    void existsByNumeroTransaccion_ShouldReturnTrue_WhenExists() {
        assertTrue(transaccionRepository.existsByNumeroTransaccion(123));
    }

    @Test
    void existsByNumeroTransaccion_ShouldReturnFalse_WhenDoesNotExist() {
        assertFalse(transaccionRepository.existsByNumeroTransaccion(999));
    }

    @Test
    void buscarPorFiltro_ShouldReturnMatchingTransaccion() {
        List<TransaccionEntity> result = transaccionRepository.buscarPorFiltro("Juan");
        assertFalse(result.isEmpty());
        assertEquals(transaccion.getIdTransaccion(), result.get(0).getIdTransaccion());
    }

    @Test
    void countByCliente_IdCliente_ShouldReturnCorrectCount() {
        long count = transaccionRepository.countByCliente_IdCliente(cliente.getIdCliente());
        assertEquals(1, count);
    }

    @Test
    void save_ShouldPersistTransaccion() {
        TransaccionEntity nueva = TransaccionEntity.builder()
                .numeroTransaccion(456)
                .montoPesos(2000)
                .giroComercio("Comercio 2")
                .fechaTransaccion(LocalDateTime.now())
                .cliente(cliente)
                .build();

        TransaccionEntity saved = transaccionRepository.save(nueva);
        assertNotNull(saved.getIdTransaccion());
        assertEquals(2000, saved.getMontoPesos());
    }
}

package com.cpalacios.tenpo.app.persistence.repository;


import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private ClienteEntity cliente;

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada prueba
        clienteRepository.deleteAll();

        // Crear un cliente de prueba
        cliente = ClienteEntity.builder()
                .nombreTenpista("Juan Perez")
                .build();
        clienteRepository.save(cliente);
    }

    @Test
    void findByNombreTenpistaIgnoreCase_ShouldReturnCliente_WhenExists() {
        Optional<ClienteEntity> found = clienteRepository.findByNombreTenpistaIgnoreCase("juan perez");

        assertTrue(found.isPresent());
        assertEquals(cliente.getIdCliente(), found.get().getIdCliente());
        assertEquals(cliente.getNombreTenpista(), found.get().getNombreTenpista());
    }

    @Test
    void findByNombreTenpistaIgnoreCase_ShouldReturnEmpty_WhenDoesNotExist() {
        Optional<ClienteEntity> found = clienteRepository.findByNombreTenpistaIgnoreCase("No Existe");

        assertFalse(found.isPresent());
    }

    @Test
    void save_ShouldPersistCliente() {
        ClienteEntity nuevoCliente = ClienteEntity.builder()
                .nombreTenpista("Maria Lopez")
                .build();

        ClienteEntity saved = clienteRepository.save(nuevoCliente);

        assertNotNull(saved.getIdCliente());
        assertEquals("Maria Lopez", saved.getNombreTenpista());
    }
}

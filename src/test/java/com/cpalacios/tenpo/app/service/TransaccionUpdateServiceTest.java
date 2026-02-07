package com.cpalacios.tenpo.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import com.cpalacios.tenpo.app.dto.TransaccionUpdateDTO;
import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.exception.BusinessException;
import com.cpalacios.tenpo.app.mapper.TransaccionMapper;
import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;
import com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity;
import com.cpalacios.tenpo.app.persistence.repository.ClienteRepository;
import com.cpalacios.tenpo.app.persistence.repository.TransaccionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransaccionUpdateServiceTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransaccionMapper mapper;

    @InjectMocks
    private TransaccionUpdateService service;

    private TransaccionEntity transaccionEntity;
    private TransaccionUpdateDTO updateDTO;
    private ClienteEntity clienteOriginal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        clienteOriginal = ClienteEntity.builder()
                .idCliente(1L)
                .nombreTenpista("Original")
                .build();

        transaccionEntity = TransaccionEntity.builder()
                .idTransaccion(1L)
                .numeroTransaccion(100)
                .montoPesos(1000)
                .giroComercio("Comercio A")
                .fechaTransaccion(LocalDateTime.now())
                .cliente(clienteOriginal)
                .build();

        updateDTO = new TransaccionUpdateDTO();
        updateDTO.setIdTransaccion(1L);
        updateDTO.setNumeroTransaccion(200); // ahora es Integer
        updateDTO.setMontoPesos(2000);
        updateDTO.setGiroComercio("Comercio B");
        updateDTO.setFechaTransaccion(LocalDateTime.now());
        updateDTO.setNombreTenpista("NuevoCliente");
    }

    @Test
    void testActualizarTransaccion_ClienteExistente() {
        // Simular que el cliente ya existe
        ClienteEntity clienteExistente = ClienteEntity.builder()
                .idCliente(2L)
                .nombreTenpista("NuevoCliente")
                .build();

        when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccionEntity));
        when(clienteRepository.findByNombreTenpistaIgnoreCase("NuevoCliente")).thenReturn(Optional.of(clienteExistente));
        when(transaccionRepository.save(any(TransaccionEntity.class))).thenReturn(transaccionEntity);
        when(mapper.toResponse(any(TransaccionEntity.class))).thenReturn(new TransaccionResponseDTO());

        TransaccionResponseDTO result = service.actualizarTransaccion(updateDTO);

        assertNotNull(result);
        verify(transaccionRepository).findById(1L);
        verify(clienteRepository).findByNombreTenpistaIgnoreCase("NuevoCliente");
        verify(clienteRepository, never()).save(any()); // no se crea un nuevo cliente
        assertEquals(clienteExistente, transaccionEntity.getCliente());
    }

    @Test
    void testActualizarTransaccion_ClienteNuevo() {
        when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccionEntity));
        when(clienteRepository.findByNombreTenpistaIgnoreCase("NuevoCliente")).thenReturn(Optional.empty());

        // Simular guardar nuevo cliente
        ClienteEntity nuevoCliente = ClienteEntity.builder()
                .idCliente(3L)
                .nombreTenpista("NuevoCliente")
                .build();
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(nuevoCliente);
        when(transaccionRepository.save(any(TransaccionEntity.class))).thenReturn(transaccionEntity);
        when(mapper.toResponse(any(TransaccionEntity.class))).thenReturn(new TransaccionResponseDTO());

        TransaccionResponseDTO result = service.actualizarTransaccion(updateDTO);

        assertNotNull(result);
        verify(clienteRepository).save(any(ClienteEntity.class));
        assertEquals("NuevoCliente", transaccionEntity.getCliente().getNombreTenpista());
    }

    @Test
    void testActualizarTransaccion_TransaccionNoEncontrada() {
        when(transaccionRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.actualizarTransaccion(updateDTO);
        });

        assertTrue(exception.getMessage().contains("No existe una transacción"));
    }

    @Test
    void testActualizarTransaccion_ErrorGenerico() {
        when(transaccionRepository.findById(1L)).thenThrow(new RuntimeException("DB error"));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.actualizarTransaccion(updateDTO);
        });

        assertTrue(exception.getMessage().contains("no se logro actualizar"));
    }
}

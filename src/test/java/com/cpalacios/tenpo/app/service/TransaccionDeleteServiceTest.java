package com.cpalacios.tenpo.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.cpalacios.tenpo.app.exception.BusinessException;
import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;
import com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity;
import com.cpalacios.tenpo.app.persistence.repository.ClienteRepository;
import com.cpalacios.tenpo.app.persistence.repository.TransaccionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransaccionDeleteServiceTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private TransaccionDeleteService service;

    private TransaccionEntity transaccion;
    private ClienteEntity cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = ClienteEntity.builder()
                .idCliente(1L)
                .nombreTenpista("Cliente1")
                .build();

        transaccion = TransaccionEntity.builder()
                .idTransaccion(1L)
                .numeroTransaccion(100)
                .montoPesos(1000)
                .giroComercio("Comercio A")
                .cliente(cliente)
                .build();
    }

    @Test
    void testEliminarTransaccion_ClienteUnicaTransaccion() {
        when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccion));
        when(transaccionRepository.countByCliente_IdCliente(cliente.getIdCliente())).thenReturn(0L);

        String resultado = service.eliminarTransaccion(1L);

        assertEquals("La transacción con id 1 fue eliminada correctamente", resultado);

        verify(transaccionRepository).delete(transaccion);
        verify(clienteRepository).delete(cliente); // Cliente eliminado porque solo tenía una transacción
    }

    @Test
    void testEliminarTransaccion_ClienteVariasTransacciones() {
        when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccion));
        when(transaccionRepository.countByCliente_IdCliente(cliente.getIdCliente())).thenReturn(2L);

        String resultado = service.eliminarTransaccion(1L);

        assertEquals("La transacción con id 1 fue eliminada correctamente", resultado);

        verify(transaccionRepository).delete(transaccion);
        verify(clienteRepository, never()).delete(cliente); // Cliente NO eliminado
    }

    @Test
    void testEliminarTransaccion_TransaccionNoEncontrada() {
        when(transaccionRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.eliminarTransaccion(1L);
        });

        assertTrue(exception.getMessage().contains("No existe una transacción"));
    }

    @Test
    void testEliminarTransaccion_ErrorGenerico() {
        when(transaccionRepository.findById(1L)).thenThrow(new RuntimeException("DB error"));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.eliminarTransaccion(1L);
        });

        assertTrue(exception.getMessage().contains("no se logro eliminar"));
    }
}

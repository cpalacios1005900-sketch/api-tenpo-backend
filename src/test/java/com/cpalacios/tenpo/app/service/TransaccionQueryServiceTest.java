package com.cpalacios.tenpo.app.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.exception.BusinessException;
import com.cpalacios.tenpo.app.mapper.TransaccionMapper;
import com.cpalacios.tenpo.app.persistence.entity.ClienteEntity;
import com.cpalacios.tenpo.app.persistence.entity.TransaccionEntity;
import com.cpalacios.tenpo.app.persistence.repository.TransaccionRepository;

@ExtendWith(MockitoExtension.class)
class TransaccionQueryServiceTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private TransaccionMapper mapper;

    @InjectMocks
    private TransaccionQueryService service;

    private TransaccionEntity transaccionEntity;
    private TransaccionResponseDTO transaccionDTO;

    @BeforeEach
    void setUp() {
        ClienteEntity cliente = ClienteEntity.builder()
                .idCliente(1L)
                .nombreTenpista("Carlos")
                .build();

        transaccionEntity = TransaccionEntity.builder()
                .idTransaccion(1L)
                .numeroTransaccion(100)
                .montoPesos(5000)
                .giroComercio("Supermercado")
                .fechaTransaccion(LocalDateTime.now())
                .cliente(cliente)
                .build();

        transaccionDTO = TransaccionResponseDTO.builder()
                .idTransaccion(1L)
                .nombreTenpista("Carlos")
                .montoPesos(5000)
                .fechaTransaccion(transaccionEntity.getFechaTransaccion())
                .build();
    }

    @Test
    void listarTodas_DeberiaRetornarListaDTO() {
        // Mock repository
        when(transaccionRepository.findAll()).thenReturn(Arrays.asList(transaccionEntity));
        when(mapper.toResponse(transaccionEntity)).thenReturn(transaccionDTO);

        List<TransaccionResponseDTO> result = service.listarTodas();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Carlos", result.get(0).getNombreTenpista());

        verify(transaccionRepository, times(1)).findAll();
        verify(mapper, times(1)).toResponse(transaccionEntity);
    }

    @Test
    void listarTodas_CuandoRepoLanzaError_DeberiaLanzarBusinessException() {
        when(transaccionRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.listarTodas());
        assertEquals("No se logro consultar las transacciones", ex.getMessage());
    }

    @Test
    void buscarPorFiltro_DeberiaRetornarListaDTO() {
        String filtro = "Carlos";

        when(transaccionRepository.buscarPorFiltro(filtro)).thenReturn(Arrays.asList(transaccionEntity));
        when(mapper.toResponse(transaccionEntity)).thenReturn(transaccionDTO);

        List<TransaccionResponseDTO> result = service.buscarPorFiltro(filtro);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Carlos", result.get(0).getNombreTenpista());

        verify(transaccionRepository, times(1)).buscarPorFiltro(filtro);
        verify(mapper, times(1)).toResponse(transaccionEntity);
    }

    @Test
    void buscarPorFiltro_CuandoRepoLanzaError_DeberiaLanzarBusinessException() {
        String filtro = "Carlos";
        when(transaccionRepository.buscarPorFiltro(filtro)).thenThrow(new RuntimeException("DB error"));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.buscarPorFiltro(filtro));
        assertEquals("No se logro consultar las transacciones", ex.getMessage());
    }
}

package com.cpalacios.tenpo.app.controller;


import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.service.TransaccionQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionQueryControllerTest {

    @Mock
    private TransaccionQueryService queryService;

    @InjectMocks
    private TransaccionQueryController queryController;

    private TransaccionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        responseDTO = new TransaccionResponseDTO();
        responseDTO.setIdTransaccion(1L);
        responseDTO.setNumeroTransaccion(12345);
        responseDTO.setMontoPesos(50000);
        responseDTO.setGiroComercio("Comercio Test");
        responseDTO.setNombreTenpista("Cliente Test");
    }

    @Test
    void listarTodas_ShouldReturnList() {
        // Mockear el servicio
        when(queryService.listarTodas()).thenReturn(List.of(responseDTO));

        // Llamar al controlador
        ResponseEntity<List<TransaccionResponseDTO>> response = queryController.listarTodas();

        // Verificaciones
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDTO, response.getBody().get(0));

        verify(queryService, times(1)).listarTodas();
    }

    @Test
    void buscarPorFiltro_ShouldReturnFilteredList() {
        String filtro = "Cliente";

        // Mockear el servicio
        when(queryService.buscarPorFiltro(filtro)).thenReturn(List.of(responseDTO));

        // Llamar al controlador
        ResponseEntity<List<TransaccionResponseDTO>> response = queryController.buscar(filtro);

        // Verificaciones
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDTO, response.getBody().get(0));

        verify(queryService, times(1)).buscarPorFiltro(filtro);
    }

    
}

package com.cpalacios.tenpo.app.controller;


import com.cpalacios.tenpo.app.dto.TransaccionRequestDTO;
import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.service.TransaccionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionControllerTest {

    @Mock
    private TransaccionService transaccionService;

    @InjectMocks
    private TransaccionController transaccionController;

    private TransaccionRequestDTO requestDTO;
    private TransaccionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un DTO de ejemplo para request
        requestDTO = new TransaccionRequestDTO();
        requestDTO.setNumeroTransaccion(12345);
        requestDTO.setMontoPesos(50000);
        requestDTO.setGiroComercio("Comercio Test");
        requestDTO.setNombreTenpista("Cliente Test");

        // Crear un DTO de ejemplo para response
        responseDTO = new TransaccionResponseDTO();
        responseDTO.setIdTransaccion(1L);
        responseDTO.setNumeroTransaccion(12345);
        responseDTO.setMontoPesos(50000);
        responseDTO.setGiroComercio("Comercio Test");
        responseDTO.setNombreTenpista("Cliente Test");
    }

    @Test
    void crearTransaccion_ShouldReturnCreated() {
        // Mockear el comportamiento del servicio
        when(transaccionService.crearTransaccion(requestDTO)).thenReturn(responseDTO);

        // Llamar al controlador
        ResponseEntity<TransaccionResponseDTO> response = transaccionController.crear("CLIENT123", requestDTO);

        // Verificaciones
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());

        // Verificar que el servicio fue llamado exactamente una vez
        verify(transaccionService, times(1)).crearTransaccion(requestDTO);
    }

    @Test
    void crearTransaccion_ShouldThrowException_WhenServiceFails() {
        // Mockear excepción
        when(transaccionService.crearTransaccion(requestDTO))
                .thenThrow(new RuntimeException("Error inesperado"));

        // Verificar que el controlador lanza la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                transaccionController.crear("CLIENT123", requestDTO)
        );

        assertEquals("Error inesperado", exception.getMessage());
    }
}

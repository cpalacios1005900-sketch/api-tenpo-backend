package com.cpalacios.tenpo.app.controller;


import com.cpalacios.tenpo.app.dto.TransaccionResponseDTO;
import com.cpalacios.tenpo.app.dto.TransaccionUpdateDTO;
import com.cpalacios.tenpo.app.service.TransaccionUpdateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionUpdateControllerTest {

    @Mock
    private TransaccionUpdateService updateService;

    @InjectMocks
    private TransaccionUpdateController updateController;

    private TransaccionUpdateDTO updateDTO;
    private TransaccionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        updateDTO = new TransaccionUpdateDTO();
        updateDTO.setIdTransaccion(1L);
        updateDTO.setNumeroTransaccion(12345);
        updateDTO.setMontoPesos(50000);
        updateDTO.setGiroComercio("Comercio Test");
        updateDTO.setNombreTenpista("Cliente Test");

        responseDTO = new TransaccionResponseDTO();
        responseDTO.setIdTransaccion(1L);
        responseDTO.setNumeroTransaccion(12345);
        responseDTO.setMontoPesos(50000);
        responseDTO.setGiroComercio("Comercio Test");
        responseDTO.setNombreTenpista("Cliente Test");
    }

    @Test
    void actualizarTransaccion_ShouldReturnUpdatedDTO() {
        // Mockear servicio
        when(updateService.actualizarTransaccion(updateDTO)).thenReturn(responseDTO);

        // Llamar al controlador
        ResponseEntity<TransaccionResponseDTO> response = updateController.actualizar("CLIENT123", updateDTO);

        // Verificaciones
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());

        verify(updateService, times(1)).actualizarTransaccion(updateDTO);
    }

    @Test
    void actualizarTransaccion_ShouldThrowException_WhenServiceFails() {
        // Mockear excepción en el servicio
        when(updateService.actualizarTransaccion(updateDTO)).thenThrow(new RuntimeException("Error inesperado"));

        // Verificar que el controlador lanza la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                updateController.actualizar("CLIENT123", updateDTO)
        );

        assertEquals("Error inesperado", exception.getMessage());

        verify(updateService, times(1)).actualizarTransaccion(updateDTO);
    }
}

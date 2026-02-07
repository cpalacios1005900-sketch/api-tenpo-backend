package com.cpalacios.tenpo.app.controller;



import com.cpalacios.tenpo.app.service.TransaccionDeleteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionDeleteControllerTest {

    @Mock
    private TransaccionDeleteService deleteService;

    @InjectMocks
    private TransaccionDeleteController deleteController;

    private final Long idTransaccion = 1L;
    private final String mensaje = "La transacción con id 1 fue eliminada correctamente";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void eliminarTransaccion_ShouldReturnOkResponse() {
        // Mockear comportamiento del servicio
        when(deleteService.eliminarTransaccion(idTransaccion)).thenReturn(mensaje);

        // Llamar al controlador
        ResponseEntity<Map<String, String>> response = deleteController.eliminar("CLIENT123", idTransaccion);

        // Verificaciones
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mensaje, response.getBody().get("message"));

        // Verificar que el servicio fue llamado exactamente una vez
        verify(deleteService, times(1)).eliminarTransaccion(idTransaccion);
    }

    @Test
    void eliminarTransaccion_ShouldThrowException_WhenServiceFails() {
        // Mockear excepción en el servicio
        when(deleteService.eliminarTransaccion(idTransaccion)).thenThrow(new RuntimeException("Error inesperado"));

        // Verificar que el controlador lanza la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                deleteController.eliminar("CLIENT123", idTransaccion)
        );

        assertEquals("Error inesperado", exception.getMessage());

        // Verificar que el servicio fue llamado
        verify(deleteService, times(1)).eliminarTransaccion(idTransaccion);
    }
}

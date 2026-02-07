package com.cpalacios.tenpo.app.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cpalacios.tenpo.app.dto.TransaccionRequestDTO;
import com.cpalacios.tenpo.app.exception.BusinessException;
import com.cpalacios.tenpo.app.mapper.TransaccionMapper;
import com.cpalacios.tenpo.app.persistence.repository.ClienteRepository;
import com.cpalacios.tenpo.app.persistence.repository.TransaccionRepository;

@ExtendWith(MockitoExtension.class)
class TransaccionServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    TransaccionRepository transaccionRepository;

    @Mock
    TransaccionMapper mapper;

    @InjectMocks
    TransaccionService service;

    @Test
    void debeLanzarErrorSiSupera100Transacciones() {

        when(transaccionRepository
            .count())
            .thenReturn(100L);

        TransaccionRequestDTO dto = TransaccionRequestDTO.builder()
                .nombreTenpista("JUAN")
                .numeroTransaccion(1)
                .montoPesos(1000)
                .giroComercio("SUPERMERCADO")
                .fechaTransaccion(LocalDateTime.now())
                .build();

        assertThrows(
                BusinessException.class,
                () -> service.crearTransaccion(dto)
        );
    }
}

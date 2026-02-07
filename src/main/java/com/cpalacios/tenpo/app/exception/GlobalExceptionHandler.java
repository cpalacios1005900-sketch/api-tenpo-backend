package com.cpalacios.tenpo.app.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

/**
 * Manejador global de excepciones para la aplicación.
 *
 * <p>
 * Esta clase centraliza el tratamiento de excepciones lanzadas desde
 * los controladores y servicios, permitiendo traducir errores de la
 * capa de negocio en respuestas HTTP coherentes y controladas.
 * </p>
 *
 * <p>
 * El uso de {@link RestControllerAdvice} garantiza que los errores
 * sean manejados de forma transversal, evitando duplicación de código
 * y mejorando la mantenibilidad del sistema.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de tipo {@link BusinessException}.
     *
     * <p>
     * Este método se invoca automáticamente cuando se lanza una excepción
     * de negocio desde cualquier controlador o servicio.
     * La excepción se traduce en una respuesta HTTP 400 (Bad Request),
     * devolviendo un mensaje claro y entendible para el consumidor de la API.
     * </p>
     *
     * <p>
     * El cuerpo de la respuesta sigue un formato simple clave–valor,
     * facilitando su consumo por clientes frontend o integraciones externas.
     * </p>
     *
     * @param ex excepción de negocio lanzada durante la ejecución
     * @return {@link ResponseEntity} con el detalle del error
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusiness(
            BusinessException ex
    ) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", ex.getMessage()));
    }
    
    /**
     * Maneja errores de validación (@NotNull, @NotBlank, etc.).
     *
     * @param ex excepción lanzada por Spring al fallar la validación
     * @return respuesta HTTP 400 con detalle de errores por campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult()
          .getAllErrors()
          .forEach(error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              fieldErrors.put(fieldName, errorMessage);
          });

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation error");
        response.put("errors", fieldErrors);

        return ResponseEntity.badRequest().body(response);
    }
    
    /**
     * Maneja errores de validación de parámetros (@NotBlank, @NotNull, etc.)
     * que lanzan ConstraintViolationException.
     *
     * @param ex excepción de validación
     * @return JSON con el mensaje de error
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> error = new HashMap<>();
        // Toma el primer mensaje de validación
        String mensaje = ex.getConstraintViolations().iterator().next().getMessage();
        error.put("error", mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

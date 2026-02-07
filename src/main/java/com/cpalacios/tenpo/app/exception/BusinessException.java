package com.cpalacios.tenpo.app.exception;

/**
 * Excepción de negocio utilizada para representar errores controlados
 * dentro del dominio de la aplicación.
 *
 * <p>
 * Esta excepción se lanza cuando una regla de negocio no se cumple,
 * por ejemplo: violaciones de límites, estados inválidos o condiciones
 * que deben ser comunicadas al consumidor de la API de forma clara
 * y controlada.
 * </p>
 *
 * <p>
 * Al extender de {@link RuntimeException}, permite que las excepciones
 * de negocio se propaguen sin necesidad de ser declaradas explícitamente,
 * manteniendo el código de servicio limpio y legible.
 * </p>
 *
 * <p>
 * Normalmente, esta excepción es capturada por un manejador global
 * (por ejemplo, mediante {@code @RestControllerAdvice}) para traducirla
 * en una respuesta HTTP adecuada (como 400 Bad Request).
 * </p>
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -4789264085223177207L;

	/**
     * Construye una nueva excepción de negocio con un mensaje descriptivo.
     *
     * <p>
     * El mensaje debe ser claro y entendible, ya que normalmente será
     * retornado al consumidor de la API como parte de la respuesta de error.
     * </p>
     *
     * @param message descripción del error de negocio ocurrido
     */
    public BusinessException(String message) {
        super(message);
    }
}

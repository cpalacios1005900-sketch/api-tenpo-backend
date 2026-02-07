package com.cpalacios.tenpo.app.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interceptor para aplicar Rate Limiting (límite de solicitudes) por cliente.
 *
 * <p>
 * Este interceptor limita a un máximo de {@value #MAX_REQUESTS} solicitudes por minuto
 * por cliente. El cliente se identifica mediante el header HTTP "X-Client-Id".
 * Si un cliente excede el límite, se devuelve HTTP 429 (Too Many Requests).
 * </p>
 *
 * <p>
 * Implementa la interfaz {@link HandlerInterceptor} de Spring MVC para
 * interceptar las solicitudes antes de que lleguen al controlador.
 * </p>
 */
@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    /** Número máximo de solicitudes permitidas por cliente en la ventana de tiempo */
    private static final int MAX_REQUESTS = 3;

    /** Ventana de tiempo en milisegundos (1 minuto) */
    private static final long WINDOW_MILLIS = 60 * 1000; // 1 minuto

    /**
     * Mapa que almacena la información de cada cliente.
     *
     * <p>
     * Clave: ID del cliente (header "X-Client-Id")  
     * Valor: información de la ventana de solicitudes del cliente
     * </p>
     */
    private final Map<String, ClientRequestInfo> clients = new ConcurrentHashMap<>();
    
    
    /**
     * Conjunto de patrones de URLs que no se tomarán en cuenta para el Rate Limiting.
     * Puedes usar expresiones simples que contengan parte de la URL.
     */
    private static final Set<String> EXCLUDED_PATHS = Set.of(
			"/actuator", "/swagger-ui", "/swagger-ui.html", "/v3/api-docs", "/api/public", "/api/transacciones/all",
			"/api/transacciones/search"
    );

    /**
     * Método interceptador que se ejecuta antes de procesar la solicitud.
     *
     * @param request  la solicitud HTTP
     * @param response la respuesta HTTP
     * @param handler  el manejador del endpoint
     * @return {@code true} si se permite la solicitud; {@code false} si se bloquea
     * @throws Exception en caso de error
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // ✅ 1. PERMITIR PREFLIGHT SIN VALIDACIONES
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String path = request.getRequestURI();

        // 2. Excluir paths
        if (EXCLUDED_PATHS.stream().anyMatch(path::contains)) {
            return true;
        }

        // 3. Validar header SOLO para requests reales
        String clientId = request.getHeader("X-Client-Id");
        if (clientId == null || clientId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Client ID header missing");
            return false;
        }

        long now = Instant.now().toEpochMilli();

        clients.compute(clientId, (key, info) -> {
            if (info == null || now - info.startTime > WINDOW_MILLIS) {
                return new ClientRequestInfo(now, 1);
            } else {
                info.requestCount++;
                return info;
            }
        });

        ClientRequestInfo info = clients.get(clientId);

        if (info.requestCount > MAX_REQUESTS) {
            response.setStatus(429);
            response.getWriter().write("Ha excedido el máximo de solicitudes por minuto");
            return false;
        }

        return true;
    }


    /**
     * Clase interna que almacena información de solicitudes por cliente.
     */
    private static class ClientRequestInfo {
        /** Timestamp de la primera solicitud en la ventana actual */
        long startTime;

        /** Número de solicitudes realizadas en la ventana actual */
        int requestCount;

        /**
         * Constructor de la clase.
         *
         * @param startTime    timestamp de la primera solicitud
         * @param requestCount contador de solicitudes
         */
        ClientRequestInfo(long startTime, int requestCount) {
            this.startTime = startTime;
            this.requestCount = requestCount;
        }
    }
}

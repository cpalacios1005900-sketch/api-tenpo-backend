package com.cpalacios.tenpo.app.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración global de OpenAPI (Swagger).
 *
 * <p>
 * Define la información general de la API, como título, versión
 * y datos de contacto.
 * </p>
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Transacciones Tenpo")
                        .description("API REST para la gestión de clientes y transacciones financieras")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@tenpo.com")
                        )
                );
    }
}

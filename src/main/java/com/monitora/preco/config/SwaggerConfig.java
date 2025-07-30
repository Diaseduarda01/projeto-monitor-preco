package com.monitora.preco.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Monitora Preço",
                description = "Documentação da API para gerenciamento de usuários e autenticação com JWT.",
                version = "v1.0",
                contact = @Contact(name = "Equipe Monitora", email = "suporte@monitora.com"),
                license = @License(name = "Apache 2.0", url = "http://springdoc.org")
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        },
        servers = {
                @Server(url = "http://localhost:8004", description = "Ambiente Local")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT Token. Exemplo: Bearer eyJhbGciOiJIUzI1NiJ9..."
)
public class SwaggerConfig {
}

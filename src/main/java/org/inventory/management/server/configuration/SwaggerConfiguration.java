package org.inventory.management.server.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class SwaggerConfiguration {
    @Value("${application.app.devServer}")
    private String devServerUrl;
    @Bean
    public OpenAPI openAPI() {
        Server devServer = new io.swagger.v3.oas.models.servers.Server();
        devServer.setUrl(devServerUrl);
        SecurityScheme securityScheme = new SecurityScheme()
                .name("token")
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT");

        // Define the security requirement
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("token");

        return new OpenAPI().info(new Info().title("Inventory Management Server API").version("1.0")).
                servers(List.of(devServer))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("token", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}


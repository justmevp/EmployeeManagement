package com.example.management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                SecurityScheme apiKeyScheme = new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER);

                return new OpenAPI()
                                .info(new Info()
                                                .title("Employee Management API")
                                                .version("1.0")
                                                .description("API for managing employees"))
                                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                                .components(new Components().addSecuritySchemes("Authorization", apiKeyScheme));
        }
}

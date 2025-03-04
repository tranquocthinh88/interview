package com.code.bank.configs;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI getOpenApiDocumentation() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Bank API")
                        .description("API for Bank")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Bank IT").email("trungthinh2k2@gmail.com")))
                .externalDocs(new ExternalDocumentation().description("Bank API Documentation").url("https://bankIT.com"));
    }
}

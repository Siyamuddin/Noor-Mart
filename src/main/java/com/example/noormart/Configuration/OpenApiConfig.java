package com.example.noormart.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "NoorMart",
                description = "This a backend of a semi e-commerce web application named NoorMart.",
                termsOfService = "Terms of Service",
                contact = @Contact(
                        name = "UDDIN SIYAM",
                        email = "siyamuddin177@gmail.com",
                        url = "https://siyamuddin.netlify.app"
                )

        ),
        servers = {@Server(
                description = "Poduction Env",
                url = "http://localhost:5000"

        )}
)
@SecurityScheme(
        name = "JWT-Auth",
        description = "JWT Authentication Description",
        scheme ="bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in= SecuritySchemeIn.QUERY
)
public class OpenApiConfig {
}

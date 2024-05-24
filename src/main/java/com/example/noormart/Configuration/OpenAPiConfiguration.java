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
                contact = @Contact(
                        name = "UDDIN SIYAM",
                        email = "siyamuddin177gmail.com",
                        url = "https://siyamuddin.netlify.app"
                ),
                description = "This is a semi-ecommerce non-profit retail halal food shop.",
                title = "NoorMart",
                version = "1.0",
                termsOfService = "Terms of Service"
        ),
        servers ={
                @Server(
                        description = "Production",
                        url = "https://noor-mart-production.up.railway.app/"
                ),
                @Server(
                        description = "Local Dev",
                        url = "http://localhost:9090/"
                )
        }
)
@SecurityScheme(
        name = "JWT-Auth",
        description = "JWT Authentication Description",
        scheme ="bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in= SecuritySchemeIn.QUERY
)
public class OpenAPiConfiguration {
}

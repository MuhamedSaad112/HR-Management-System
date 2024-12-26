package com.hrapp.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@OpenAPIDefinition // OpenAPI definition annotation to specify metadata and configuration
public class OpenApiConfig {

    // Security scheme name for OAuth token authentication
    private static final String SECURITY_SCHEME_NAME = "Bearer oAuth Token";

    /**
     * Custom OpenAPI configuration.
     *
     * @param appDescription - Application description from application.properties
     * @param appVersion     - Application version from application.properties
     * @return OpenAPI object with custom configuration
     */
    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String appDescription,
                                 @Value("${application-version}") String appVersion) {
        return new OpenAPI()
                // API information with title, version, description, contact, license
                .info(new Info()
                        .title("HR Management System") // API title
                        .version(appVersion) // API version
                        .contact(getContact()) // Contact information
                        .description(appDescription) // Application description
                        .termsOfService("http://swagger.io/terms/") // Terms of Service URL
                        .license(getLicense())) // License information

                // Security configuration for Bearer OAuth token
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME, Arrays.asList("read", "write")))
                .components(
                        new Components()
                                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                        new SecurityScheme().name(SECURITY_SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP) // HTTP authentication
                                                .scheme("bearer") // Bearer token scheme
                                                .bearerFormat("JWT") // JWT format for the token
                                ));
    }

    /**
     * Method to build contact information for the API.
     *
     * @return Contact object with details
     */
    private Contact getContact() {
        Contact contact = new Contact();
        contact.setEmail("m.saad1122003@gmail.com"); // Contact email
        contact.setName("Mohamed Saad"); // Contact name
        contact.setUrl("https://github.com/MuhamedSaad112/hrapp"); // Contact URL
        contact.setExtensions(Collections.emptyMap()); // Extensions if any
        return contact;
    }

    /**
     * Method to build license information for the API.
     *
     * @return License object with details
     */
    private License getLicense() {
        License license = new License();
        license.setName("Apache License, Version 2.0"); // License name
        license.setUrl("http://www.apache.org/licenses/LICENSE-2.0"); // License URL
        license.setExtensions(Collections.emptyMap()); // Extensions if any
        return license;
    }
}

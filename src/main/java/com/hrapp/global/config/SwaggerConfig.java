package com.hrapp.global.config;


import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userManagementApi() {
        String packagesToscan[] = {"com.hrapp.global.controller"};
        return GroupedOpenApi.builder()
                .group("global API Only")
                .packagesToScan(packagesToscan)
                .addOperationCustomizer(appTokenHeaderParam())
                .build();
    }

    @Bean
    public GroupedOpenApi setupApi() {
        String packagesToscan[] = {"com.hrapp.global"};
        return GroupedOpenApi.builder()
                .group("HR API")
                .packagesToScan(packagesToscan)
                .addOperationCustomizer(appTokenHeaderParam())
                .build();
    }

    @Bean
    public OperationCustomizer appTokenHeaderParam() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            Parameter headerParameter = new Parameter().in(ParameterIn.HEADER.toString()).required(false).
                    schema(new StringSchema()._default("app_token_header_default_value")).name("app_token_header").description("App Token Header");
            operation.addParametersItem(headerParameter);
            return operation;
        };
    }

}
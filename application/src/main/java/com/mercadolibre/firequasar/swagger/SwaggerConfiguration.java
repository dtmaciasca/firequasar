package com.mercadolibre.firequasar.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.mercadolibre.firequasar.rest"))
                .paths(PathSelectors.any()).build().apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Fire Quasar Service API",
                "Fire Quasar Service API Description",
                "1.0",
                "",
                new Contact("Deisy Tatiana Macias Cardenas", "https://www.linkedin.com/in/deisy-tatiana-macias-cardenas-9ab732160/", "tatianacardenas08@gmail.com"),
                "",
                "",
                Collections.<VendorExtension>emptyList()
        );
    }
}

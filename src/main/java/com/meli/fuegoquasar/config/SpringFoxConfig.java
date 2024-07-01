package com.meli.fuegoquasar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.meli.fuegoquasar.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                ;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Operacion Fuego de Quasar API",
                "Programa en JAVA que retorna la fuente y contenido de un mensaje de auxilio determinado.",
                "1.0",
                null,
                new Contact("Sofia Aguirre", "https://www.linkedin.com/in/aguirresofia/", "sofiaaguirre1@hotmail.com"),
                "LICENSE",
                "http://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList()
        );
    }

}
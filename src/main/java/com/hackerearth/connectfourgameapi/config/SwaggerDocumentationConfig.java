package com.hackerearth.connectfourgameapi.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Connect 4 Game")
                .description("Connect Four is a two-player connection board game, in which the players choose a color " +
                        "and then take turns dropping colored discs into a seven-column, six-row vertically suspended" +
                        " grid. The pieces fall straight down, occupying the lowest available space within the column." +
                        " The objective of the game is to be the first to form a horizontal, vertical, or diagonal line" +
                        " of four of one's own discs."+"\n Yellow always goes 1st or every valid odd move." +
                        " Red always goes second or every valid even move.")
                .license("")
                .licenseUrl("http://unlicense.org")
                .termsOfServiceUrl("")

                .version("1.0.0")
                .contact(new Contact("Adarsh Srivastava", "", "adarshsrivastava125@gmail.com"))
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.withClassAnnotation(ControllerAdvice.class)))
                .apis(RequestHandlerSelectors.basePackage("com.hackerearth.connectfourgameapi"))
                .build()
                .apiInfo(apiInfo());
    }

}


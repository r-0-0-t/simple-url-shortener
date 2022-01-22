package com.ishan.simpleurlshortener;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Simple Url Shortener", version = "1.0.0", description = "Simple url shortener for assignment"))
public class SimpleUrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleUrlShortenerApplication.class, args);
    }

}

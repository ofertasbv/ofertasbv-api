package com.br.oferta.api;

import com.br.oferta.api.util.upload.property.OfertaApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(OfertaApiProperty.class)
public class OfertaApiApplication {

    private static ApplicationContext APPLICATION_CONTEXT;

    public static void main(String[] args) {
        System.out.println("UNICIANDO API - OFERTASBV - E-COMMERCE - 24-DEZEMBRO-2020");
        APPLICATION_CONTEXT = SpringApplication.run(OfertaApiApplication.class, args);
    }

    public static <T> T getBean(Class<T> type) {
        return APPLICATION_CONTEXT.getBean(type);
    }
}

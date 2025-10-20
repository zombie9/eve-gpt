package com.evegpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EveGptApplication {

    public static void main(String[] args) {
        SpringApplication.run(EveGptApplication.class, args);
    }
}
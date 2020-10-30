package com.mercadolibre.firequasar.application;

import com.mercadolibre.firequasar.jpa.SatelliteRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.mercadolibre.firequasar.rest",
        "com.mercadolibre.firequasar.swagger",
        "com.mercadolibre.firequasar.web",
        "com.mercadolibre.firequasar.jpa",
        "com.mercadolibre.firequasar.service"
})
@EnableJpaRepositories(basePackageClasses = {SatelliteRepository.class})
public class FireQuasarApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FireQuasarApplication.class, args);
    }
}

package com.epam.petclinic.clinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Main spring boot application class.
 */
@SpringBootApplication
@EnableEurekaClient
public class PetClinicClinicApplication {

    /**
     * Main launcher method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PetClinicClinicApplication.class, args);
    }
}

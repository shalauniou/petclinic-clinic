package com.epam.petclinic.clinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PetClinicClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetClinicClinicApplication.class, args);
    }
}

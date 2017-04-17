package com.epam.petclinic.clinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableEurekaClient
public class PetClinicClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetClinicClinicApplication.class, args);
    }
}

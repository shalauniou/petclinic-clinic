package com.epam.petclinic.clinic.controller;

import com.epam.petclinic.clinic.model.ClinicService;
import com.epam.petclinic.clinic.repository.ClinicServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Clinic Service controller.
 *
 * Date: 4/18/2017
 *
 * @author Stanislau Halauniou
 */
@RestController
@RequestMapping("/services")
public class ClinicServiceController {

    @Autowired
    private ClinicServiceRepository repository;

    /**
     * Returns all services.
     *
     * @return services
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @CrossOrigin
    public Iterable<ClinicService> getAll() {
        return repository.findAll();
    }
}

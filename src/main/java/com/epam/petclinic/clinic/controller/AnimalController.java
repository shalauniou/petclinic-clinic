package com.epam.petclinic.clinic.controller;

import com.epam.petclinic.clinic.model.Animal;
import com.epam.petclinic.clinic.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Animal controller.
 * <p/>
 * Date: 4/18/2017
 *
 * @author Stanislau Halauniou
 */
@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Animal> getAll() {
        return repository.findAll();
    }
}

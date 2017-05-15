package com.epam.petclinic.clinic.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.epam.petclinic.clinic.model.Animal;
import com.epam.petclinic.clinic.model.ClinicService;
import com.epam.petclinic.clinic.repository.AnimalRepository;
import com.epam.petclinic.clinic.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Animal controller.
 *
 * Date: 4/18/2017
 *
 * @author Stanislau Halauniou
 */
@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private OfferRepository offerRepository;

    /**
     * Returns all animals.
     *
     * @return all animals
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @CrossOrigin
    public Iterable<Animal> getAll() {
        return animalRepository.findAll();
    }

    /**
     * Returns clinic services by animal id.
     *
     * @param id animal id
     * @return clinic services
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}/services", method = RequestMethod.GET)
    public Set<ClinicService> getServicesByAnimalId(@PathVariable String id) {
        Set<ClinicService> services = new HashSet<>();
        Optional.ofNullable(offerRepository.findOffersByAnimalId(id))
                .ifPresent(offers -> offers.stream().forEach(offer -> services.add(offer.getClinicService())));
        return services;
    }
}

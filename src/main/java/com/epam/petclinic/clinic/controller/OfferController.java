package com.epam.petclinic.clinic.controller;

import com.epam.petclinic.clinic.model.Offer;
import com.epam.petclinic.clinic.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Offer controller.
 * <p/>
 * Date: 4/12/2017
 *
 * @author Stanislau Halauniou
 */
//TODO: Might be removed
@RestController
@RequestMapping("/offer")
class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Offer add(@RequestBody Offer offer) {
        return offerRepository.save(offer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        offerRepository.delete(id);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Offer getById(@PathVariable String id) {
        return offerRepository.findOne(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Offer> getAll() {
        return (List)offerRepository.findAll();
    }

    @RequestMapping(value = "/get/clinic/{clinicId}", method = RequestMethod.GET)
    public List<Offer> getOffersByClinicId(@PathVariable String clinicId) {
        return offerRepository.findByClinicId(clinicId);
    }

}

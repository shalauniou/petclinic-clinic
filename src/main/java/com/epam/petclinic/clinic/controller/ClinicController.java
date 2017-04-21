package com.epam.petclinic.clinic.controller;

import com.epam.petclinic.clinic.model.Clinic;
import com.epam.petclinic.clinic.model.Offer;
import com.epam.petclinic.clinic.repository.ClinicRepository;
import com.epam.petclinic.clinic.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Clinic controller.
 * <p>
 * Date: 2017-04-13
 *
 * @author Stanislau_Halauniou
 */
@RestController
@RequestMapping("/clinics")
class ClinicController {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private OfferRepository offerRepository;

    //TODO: add transaction, investigate cascading
    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Clinic create(@RequestBody Clinic clinic) {
        Clinic createdClinic = clinicRepository.save(clinic);
        List<Offer> offers = clinic.getOffers();
        offers.stream().forEach(offer -> offer.setClinic(clinic));
        offerRepository.save(offers);
        clinic.setId(createdClinic.getId());
        return clinic;
    }

    //TODO: add transaction, investigate cascading
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Clinic update(@RequestBody Clinic clinic, @PathVariable String id) {
        Clinic clinicForUpdate = clinicRepository.findOne(id);
        offerRepository.delete(clinicForUpdate.getOffers());
        List<Offer> offers = clinic.getOffers();
        clinic.setId(id);
        offers.stream().forEach(offer -> offer.setClinic(clinic));
        offerRepository.save(offers);
        clinicRepository.save(clinic);
        return clinic;
    }

    //TODO: add transaction, investigate cascading
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        Clinic clinicForDelete = clinicRepository.findOne(id);
        offerRepository.delete(clinicForDelete.getOffers());
        clinicRepository.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Clinic> getAll() {
        return clinicRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Clinic getClinicWithOffers(@PathVariable String id) {
        return clinicRepository.findOne(id);
    }

}

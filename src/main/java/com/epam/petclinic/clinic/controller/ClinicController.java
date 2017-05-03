package com.epam.petclinic.clinic.controller;

import com.epam.petclinic.clinic.model.Clinic;
import com.epam.petclinic.clinic.repository.ClinicRepository;
import com.epam.petclinic.clinic.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Clinic controller.
 *
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

    /**
     * Creates clinic.
     *
     * @param clinic clinic
     * @return created clinic
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @CrossOrigin
    public Clinic create(@RequestBody Clinic clinic) {
        Optional.ofNullable(clinic.getOffers()).ifPresent(
                offers -> offers.stream().forEach(offer -> offer.setClinic(clinic)));
        return clinicRepository.save(clinic);
    }

    /**
     * Updates clinic with new offers.
     *
     * @param clinic contains new offers, name, address
     * @param id of updated clinic
     * @return updated clinic
     */
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @CrossOrigin
    public Clinic update(@RequestBody Clinic clinic, @PathVariable String id) {
        clinicRepository.delete(id);
        Optional.ofNullable(clinic.getOffers()).ifPresent(
                offers -> offers.stream().forEach(offer -> offer.setClinic(clinic)));
        clinicRepository.save(clinic);
        return clinic;
    }

    /**
     * Deletes clinic and offers connected to clinic.
     *
     * @param id clinic id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @CrossOrigin
    public void delete(@PathVariable String id) {
        Clinic clinicForDelete = clinicRepository.findOne(id);
        clinicRepository.delete(clinicForDelete);
    }

    /**
     * Returns list of clinic ids by animal id and service's ids.
     *
     * @param animalId animal id
     * @param serviceIds list of service's ids
     * @return list of clinic ids
     */
    @CrossOrigin
    @RequestMapping(value = "/by-animal-services", method = RequestMethod.GET)
    public List<String> getClinicsByAnimalAndServices(@RequestParam("animalId") String animalId,
                                                      @RequestParam("serviceIds") List<String> serviceIds) {
        return offerRepository.findClinicByAnimalIdAndServices(animalId, serviceIds, (long) serviceIds.size());
    }

    /**
     * Returns all clinics with their offers.
     *
     * @return all clinics
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @CrossOrigin
    public Iterable<Clinic> getAll() {
        return clinicRepository.findAll();
    }

    /**
     * Returns clinic with offers.
     *
     * @param id clinic io
     * @return clinic
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @CrossOrigin
    public Clinic getClinicWithOffers(@PathVariable String id) {
        return clinicRepository.findOne(id);
    }

}

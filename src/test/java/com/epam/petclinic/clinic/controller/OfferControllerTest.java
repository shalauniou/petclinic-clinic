package com.epam.petclinic.clinic.controller;

import com.epam.petclinic.clinic.model.Animal;
import com.epam.petclinic.clinic.model.Clinic;
import com.epam.petclinic.clinic.model.ClinicService;
import com.epam.petclinic.clinic.model.Offer;
import com.epam.petclinic.clinic.repository.AnimalRepository;
import com.epam.petclinic.clinic.repository.ClinicRepository;
import com.epam.petclinic.clinic.repository.ClinicServiceRepository;
import com.epam.petclinic.clinic.repository.OfferRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests {@link OfferController}.
 * <p/>
 * Copyright (C) 2016 copyright.com
 * <p/>
 * Date: 4/14/2017
 *
 * @author Stanislau Halauniou
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerTest {

    private static final String OFFER_ADD_PATH = "/offer/add";
    private static final String OFFER_DELETE_PATH = "/offer/";
    private static final String OFFER_GET_ALL_PATH = "/offer/all/";
    private static final String GET_OFFERS_BY_CLINIC_PATH = "/offer/get/clinic/";

    private RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    private int port;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ClinicServiceRepository clinicServiceRepository;

    @Test
    public void testGetAllOffers() {
        ParameterizedTypeReference<List<Offer>> responseType = new ParameterizedTypeReference<List<Offer>>() {};
        ResponseEntity<List<Offer>> responseEntity = restTemplate.exchange(getHost() + OFFER_GET_ALL_PATH,
                HttpMethod.GET, null, responseType);

        List<Offer> offers = responseEntity.getBody();
        assertNotNull(offers);
        assertFalse(offers.isEmpty());
    }

    //@Test
    public void testAddAndDeleteOffer() {
        Clinic clinic = clinicRepository.findAll().iterator().next();
        Animal animal = animalRepository.findAll().iterator().next();
        ClinicService clinicService = clinicServiceRepository.findAll().iterator().next();

        Offer addedOffer = restTemplate.postForObject(getHost() + OFFER_ADD_PATH,
                getOffer(animal, clinic, clinicService), Offer.class);

        assertNotNull(addedOffer);

        String offerId = addedOffer.getId();

        assertNotNull(offerId);
        assertNotNull(offerRepository.findOne(offerId));

        restTemplate.delete(getHost() + OFFER_DELETE_PATH + offerId);

        assertNull(offerRepository.findOne(offerId));
    }

    @Test
    public void testGetOffersByClinicId() throws URISyntaxException {
        Clinic clinic = clinicRepository.findAll().iterator().next();
        String clinicId = clinic.getId();
        ParameterizedTypeReference<List<Offer>> responseType = new ParameterizedTypeReference<List<Offer>>() {};
        ResponseEntity<List<Offer>> offers = restTemplate.exchange(getHost() + GET_OFFERS_BY_CLINIC_PATH + clinicId,
                HttpMethod.GET, null, responseType);
        for (Offer offer : offers.getBody()) {
            assertEquals(clinicId, offer.getClinic().getId());
        }
    }

    private Offer getOffer(Animal animal, Clinic clinic, ClinicService service) {
        Offer offer = new Offer();
        offer.setAnimal(animal);
        offer.setClinicService(service);
        offer.setClinic(clinic);
        return offer;
    }

    private String getHost() {
        return "http://localhost:" + port;
    }

}

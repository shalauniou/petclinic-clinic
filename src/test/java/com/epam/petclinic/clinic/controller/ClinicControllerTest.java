package com.epam.petclinic.clinic.controller;

import com.epam.petclinic.clinic.model.Animal;
import com.epam.petclinic.clinic.model.Clinic;
import com.epam.petclinic.clinic.model.ClinicService;
import com.epam.petclinic.clinic.model.Offer;
import com.epam.petclinic.clinic.repository.AnimalRepository;
import com.epam.petclinic.clinic.repository.ClinicRepository;
import com.epam.petclinic.clinic.repository.ClinicServiceRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests {@link ClinicController}.
 *
 * Date: 4/17/2017
 *
 * @author Stanislau Halauniou
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClinicControllerTest {

    private static final String CLINIC_PATH = "/clinics/";
    private static final String CLINIC_NAME = "Clinic Name !!!";
    private static final String CLINIC_ADDRESS = "Clinic Address !!!";

    private RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    private int port;

    @Autowired
    private ClinicServiceRepository clinicServiceRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Test
    public void testGetAllClinics() {
        List clinics = restTemplate.getForObject(getHost() + CLINIC_PATH, List.class);

        assertNotNull(clinics);
        assertFalse(clinics.isEmpty());
    }

    @Test
    public void testGetClinicWithOffers() {
        Clinic clinic = clinicRepository.findAll().iterator().next();
        ParameterizedTypeReference<Clinic> responseType = new ParameterizedTypeReference<Clinic>() {
        };
        ResponseEntity<Clinic> responseEntity = restTemplate.exchange(getHost() + CLINIC_PATH + clinic.getId(),
                HttpMethod.GET, null, responseType);

        Clinic clinicResponse = responseEntity.getBody();
        assertNotNull(clinicResponse);
        List<Offer> offers = clinicResponse.getOffers();
        assertNotNull(offers);
        assertFalse(offers.isEmpty());
    }

    @Test
    public void testCreateClinic() {
        Clinic clinic = getClinic();

        Clinic createdClinic = restTemplate.postForObject(getHost() + CLINIC_PATH, clinic, Clinic.class);

        assertEquals(CLINIC_NAME, createdClinic.getName());
        assertEquals(CLINIC_ADDRESS, createdClinic.getAddress());
        String id = createdClinic.getId();

        assertNotNull(clinicRepository.findOne(id));

        restTemplate.delete(getHost() + CLINIC_PATH + id);
    }

    @Test
    public void testDeleteClinic() {
        Clinic clinic = getClinic();

        Clinic createdClinic = restTemplate.postForObject(getHost() + CLINIC_PATH, clinic, Clinic.class);

        assertEquals(CLINIC_NAME, createdClinic.getName());
        assertEquals(CLINIC_ADDRESS, createdClinic.getAddress());
        String id = createdClinic.getId();

        restTemplate.delete(getHost() + CLINIC_PATH + id);

        assertNull(clinicRepository.findOne(id));
    }

    private Clinic getClinic() {
        Animal animal = animalRepository.findAll().iterator().next();
        ClinicService clinicService = clinicServiceRepository.findAll().iterator().next();

        Clinic clinic = new Clinic();
        clinic.setName(CLINIC_NAME);
        clinic.setAddress(CLINIC_ADDRESS);
        List<Offer> offers = new ArrayList<>();
        offers.add(getOffer(animal, clinic, clinicService));
        clinic.setOffers(offers);
        return clinic;
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

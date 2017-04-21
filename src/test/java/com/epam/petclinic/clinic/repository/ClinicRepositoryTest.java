package com.epam.petclinic.clinic.repository;

import com.epam.petclinic.clinic.model.Clinic;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link ClinicRepository}.
 * <p/>
 * Date: 4/14/2017
 *
 * @author Stanislau Halauniou
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClinicRepositoryTest {

    private static final String CLINIC_NAME = "Clinic Name";
    private static final String CLINIC_ADDRESS = "Clinic Address";
    private String clinicId;

    @Autowired
    private ClinicRepository repository;

    @After
    public void setUp() {
        repository.delete(clinicId);
    }

    @Test
    public void testFindClinic() {
        Clinic clinic = repository.save(getClinic(CLINIC_NAME, CLINIC_ADDRESS));
        clinicId = clinic.getId();
        Clinic foundClinic = repository.findOne(clinicId);

        assertEquals(CLINIC_NAME, foundClinic.getName());
        assertEquals(CLINIC_ADDRESS, foundClinic.getAddress());
    }

    private Clinic getClinic(String name, String address) {
        Clinic clinic = new Clinic();
        clinic.setName(name);
        clinic.setAddress(address);
        return clinic;
    }
}

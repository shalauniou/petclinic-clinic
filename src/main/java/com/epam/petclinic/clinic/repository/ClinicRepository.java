package com.epam.petclinic.clinic.repository;

import com.epam.petclinic.clinic.model.Clinic;
import org.springframework.data.repository.CrudRepository;

/**
 * Crud repository for {@link Clinic} entity.
 *
 * Date: 4/13/2017
 *
 * @author Stanislau Halauniou
 */
public interface ClinicRepository extends CrudRepository<Clinic, String> {

}

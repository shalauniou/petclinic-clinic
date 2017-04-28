package com.epam.petclinic.clinic.repository;

import com.epam.petclinic.clinic.model.ClinicService;
import org.springframework.data.repository.CrudRepository;

/**
 * Crud repository for {@link ClinicService} entity.
 *
 * Date: 4/12/2017
 *
 * @author Stanislau Halauniou
 */
public interface ClinicServiceRepository extends CrudRepository<ClinicService, String> {
}

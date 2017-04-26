package com.epam.petclinic.clinic.repository;

import com.epam.petclinic.clinic.model.Animal;
import org.springframework.data.repository.CrudRepository;

/**
 * Crud repository for {@link Animal} entity.
 *
 * Date: 4/12/2017
 *
 * @author Stanislau Halauniou
 */
public interface AnimalRepository extends CrudRepository<Animal, String> {
}

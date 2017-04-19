package com.epam.petclinic.clinic.repository;

import com.epam.petclinic.clinic.model.Animal;
import org.springframework.data.repository.CrudRepository;

/**
 * Comment here.
 * <p/>
 * Date: 4/12/2017
 *
 * @author Stanislau Halauniou
 */
public interface AnimalRepository extends CrudRepository<Animal, String> {
}

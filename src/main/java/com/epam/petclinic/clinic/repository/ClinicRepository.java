package com.epam.petclinic.clinic.repository;

import com.epam.petclinic.clinic.model.Clinic;
import org.springframework.data.repository.CrudRepository;

/**
 * Comment here.
 * <p/>
 * Date: 4/13/2017
 *
 * @author Stanislau Halauniou
 */
public interface ClinicRepository extends CrudRepository<Clinic, String> {

    /*@Query("SELECT u FROM Clinic u LEFT JOIN FETCH u.offers where u.id=?1")
    public Clinic findClinicWithOffersById(@Param("id") String id);*/
}

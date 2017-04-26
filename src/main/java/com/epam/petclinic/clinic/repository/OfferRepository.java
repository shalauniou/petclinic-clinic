package com.epam.petclinic.clinic.repository;

import com.epam.petclinic.clinic.model.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Crud repository for {@link Offer} entity.
 *
 * Date: 4/12/2017
 *
 * @author Stanislau Halauniou
 */
public interface OfferRepository extends CrudRepository<Offer, String> {

    List<Offer> findByClinicId(@Param("clinicId") String clinicId);
}

package com.epam.petclinic.clinic.repository;

import java.util.List;

import com.epam.petclinic.clinic.model.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Crud repository for {@link Offer} entity.
 *
 * Date: 4/12/2017
 *
 * @author Stanislau Halauniou
 */
public interface OfferRepository extends CrudRepository<Offer, String> {

    /**
     * Returns list of clinic ids by animal id and service's ids.
     *
     * @param animalId animal id
     * @param services list of service's ids
     * @param count count of services
     * @return list of clinic ids
     */
    @Query("SELECT u.clinic.id FROM Offer u where u.animal.id=:animalId and u.clinicService.id in :serviceIds "
            + "group by u.clinic.id having count(*)=:count")
    List<String> findClinicByAnimalIdAndServices(@Param("animalId") String animalId,
                                                 @Param("serviceIds") List<String> services,
                                                 @Param("count") Long count);

    /**
     * Returns list of offers by animal id.
     *
     * @param id animal id
     * @return list of offers
     */
    List<Offer> findOffersByAnimalId(@Param("animalId") String id);
}

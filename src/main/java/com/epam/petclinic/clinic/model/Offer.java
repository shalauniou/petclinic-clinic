package com.epam.petclinic.clinic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.transform.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Offer entity.
 *
 * Date: 4/12/2017
 *
 * @author Stanislau Halauniou
 */
@Entity
@Table(name= "offer")
@EqualsAndHashCode
public class Offer implements Serializable {

    private String id;

    private Clinic clinic;

    private Animal animal;

    private ClinicService clinicService;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    @JsonIgnoreProperties("offers")
    public Clinic getClinic() {
        return clinic;
    }

    @ManyToOne
    public Animal getAnimal() {
        return animal;
    }

    @ManyToOne
    public ClinicService getClinicService() {
        return clinicService;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setClinicService(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("clinic", clinic)
                .append("animal", animal)
                .append("clinicService", clinicService)
                .toString();
    }
}

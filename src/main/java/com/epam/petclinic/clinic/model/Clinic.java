package com.epam.petclinic.clinic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.transform.EqualsAndHashCode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Clinic entity.
 *
 * Date: 4/12/2017
 *
 * @author Stanislau Halauniou
 */
@Entity
@Table(name = "clinic")
@EqualsAndHashCode
public class Clinic implements Serializable {

    private String id;

    private String name;

    private String address;

    private List<Offer> offers;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @OneToMany(mappedBy = "clinic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("clinic")
    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("address", address)
                .append("offers", offers)
                .toString();
    }
}

package com.example.demo.persistance.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "LOCATIONS", schema = "OT", catalog = "")
public class Location {
    private BigInteger locationId;
    private String address;
    private String postalCode;
    private String city;
    private String state;
    private Country country;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "LOCATION_ID")
    public BigInteger getLocationId() {
        return locationId;
    }

    public void setLocationId(BigInteger locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(locationId, location.locationId) && Objects.equals(address, location.address) && Objects.equals(postalCode, location.postalCode) && Objects.equals(city, location.city) && Objects.equals(state, location.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, address, postalCode, city, state);
    }

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

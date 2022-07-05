package com.example.demo.persistance.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "COUNTRIES", schema = "OT")
public class Country {
    private String countryId;
    private String countryName;
    private Region region;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "COUNTRY_ID")
    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "COUNTRY_NAME")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return countryId.equals(country.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId);
    }

    @ManyToOne
    @JoinColumn(name = "REGION_ID", referencedColumnName = "REGION_ID")
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}

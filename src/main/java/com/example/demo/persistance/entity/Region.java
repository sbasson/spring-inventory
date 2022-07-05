package com.example.demo.persistance.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "REGIONS", schema = "OT")
public class Region {
    private BigInteger regionId;
    private String regionName;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "REGION_ID")
    public BigInteger getRegionId() {
        return regionId;
    }

    public void setRegionId(BigInteger regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "REGION_NAME")
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(regionId, region.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId);
    }
}

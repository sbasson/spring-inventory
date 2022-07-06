package com.example.demo.persistance.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "WAREHOUSES", schema = "OT")
public class Warehouse {
    private BigInteger warehouseId;
    private String warehouseName;
    private Location location;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "WAREHOUSE_ID")
    public BigInteger getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(BigInteger warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Basic
    @Column(name = "WAREHOUSE_NAME")
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(warehouseId, warehouse.warehouseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId);
    }

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

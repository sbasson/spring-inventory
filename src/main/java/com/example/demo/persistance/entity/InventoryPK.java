package com.example.demo.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class InventoryPK implements Serializable {
    private BigInteger productId;
    private BigInteger warehouseId;

    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    @Column(name = "WAREHOUSE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public BigInteger getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(BigInteger warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryPK that = (InventoryPK) o;
        return productId == that.productId && warehouseId == that.warehouseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, warehouseId);
    }
}

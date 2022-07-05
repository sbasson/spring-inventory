package com.example.demo.persistance.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "INVENTORIES", schema = "OT")
public class Inventory {

    @EmbeddedId
    private InventoryPK id;
    private int quantity;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Product product;

    @MapsId("warehouseId")
    @ManyToOne
    @JoinColumn(name = "WAREHOUSE_ID", referencedColumnName = "WAREHOUSE_ID", nullable = false)
    private Warehous warehouse;

    public InventoryPK getId() {
        return id;
    }

    public void setId(InventoryPK id) {
        this.id = id;
    }

    @Basic
    @Column(name = "QUANTITY")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Warehous getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehous warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return id.equals(inventory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

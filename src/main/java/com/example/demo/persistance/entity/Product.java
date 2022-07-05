package com.example.demo.persistance.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS", schema = "OT")
public class Product {
    private BigInteger productId;
    private String productName;
    private String description;
    private Integer standardCost;
    private Integer listPrice;
    private Collection<Inventory> inventories;
    private ProductCategory productCategory;
    private Collection<OrderItem> orderItems;

    public Product(BigInteger productId, String productName, String description, Integer standardCost, Integer listPrice) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.standardCost = standardCost;
        this.listPrice = listPrice;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PRODUCT_ID")
    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "PRODUCT_NAME")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "STANDARD_COST")
    public Integer getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(Integer standardCost) {
        this.standardCost = standardCost;
    }

    @Basic
    @Column(name = "LIST_PRICE")
    public Integer getListPrice() {
        return listPrice;
    }

    public void setListPrice(Integer listPrice) {
        this.listPrice = listPrice;
    }

    @OneToMany(mappedBy = "product")
    public Collection<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(Collection<Inventory> inventories) {
        this.inventories = inventories;
    }

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID", nullable = false)
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    @OneToMany(mappedBy = "product")
    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}

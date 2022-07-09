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
public class OrderItemPK implements Serializable {
    public BigInteger orderId;
    public BigInteger itemId;

    @Column(name = "ORDER_ID")
    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    @Column(name = "ITEM_ID")
    public BigInteger getItemId() {
        return itemId;
    }

    public void setItemId(BigInteger itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPK that = (OrderItemPK) o;
        return orderId == that.orderId && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, itemId);
    }
}

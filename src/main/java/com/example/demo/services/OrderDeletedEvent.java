package com.example.demo.services;

import com.example.demo.persistance.entity.Order;

import java.math.BigInteger;

public class OrderDeletedEvent {

    private final Order order;

    public OrderDeletedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "OrderDeletedEvent{" +
                "orderId=" + order.getOrderId() +
                '}';
    }
}

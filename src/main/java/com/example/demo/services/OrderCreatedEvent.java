package com.example.demo.services;

import com.example.demo.persistance.entity.Order;

import java.math.BigInteger;

public class OrderCreatedEvent {

    private final Order order;

    public OrderCreatedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + order.getOrderId() +
                '}';
    }
}

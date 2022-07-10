package com.example.demo.services;

import com.example.demo.persistance.entity.Order;


public class OrderUpdatedEvent {

    private final Order order;

    public OrderUpdatedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "OrderUpdatedEvent{" +
                "orderId=" + order.getOrderId() +
                '}';
    }
}

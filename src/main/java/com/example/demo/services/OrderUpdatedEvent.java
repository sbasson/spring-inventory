package com.example.demo.services;

import com.example.demo.persistance.entity.Order;
import com.example.demo.utility.OrderInput;


public class OrderUpdatedEvent {

    private final Order order;
    private final OrderInput input;

    public OrderUpdatedEvent(Order order, OrderInput input) {
        this.order = order;
        this.input = input;
    }

    public Order getOrder() {
        return order;
    }

    public OrderInput getInput() { return input; }

    @Override
    public String toString() {
        return "OrderUpdatedEvent{" +
                "orderId=" + order.getOrderId() +
                '}';
    }
}

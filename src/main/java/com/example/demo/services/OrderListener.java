package com.example.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * "Forwards" VisitCreatedEvents that are fired by Spring Boot in our domain layer
 * to a reactive publisher that is used by the GraphQL layer to publish events
 * for our GraphQL Subscriptions
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */

@Component
@AllArgsConstructor
public class OrderListener {


    private final OrderPublisher orderPublisher;
    private final WarehouseService warehouseService;

    @EventListener
    public void onNewOrder(OrderCreatedEvent event) {

        orderPublisher.updateSink(event.getOrder());
        warehouseService.updateWarehouseByOrderCreated(event.getOrder());
    }

    @EventListener
    public void onDeleteOrder(OrderDeletedEvent event) {

        orderPublisher.updateSink(event.getOrder());
        warehouseService.updateWarehouseByOrderDeleted(event.getOrder());
    }

    @EventListener
    public void onUpdateOrder(OrderUpdatedEvent event) {

        orderPublisher.updateSink(event.getOrder());
        warehouseService.updateWarehouseByOrderUpdated(event.getOrder(),event.getInput());
    }
}

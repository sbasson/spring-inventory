package com.example.demo.services;

import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.repository.OrderRepository;
import com.example.demo.services.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.util.Optional;

/**
 * "Forwards" VisitCreatedEvents that are fired by Spring Boot in our domain layer
 * to a reactive publisher that is used by the GraphQL layer to publish events
 * for our GraphQL Subscriptions
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */

@Component
public class OrderPublisher {


    private final OrderRepository orderRepository;
    private final Sinks.Many<Order> sink;

    public OrderPublisher(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.sink = Sinks.many()
                .multicast()
                .onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
    }

    @EventListener
    public void onNewOrder(OrderCreatedEvent event) {
        sink.emitNext(event.getOrder(), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @EventListener
    public void onDeleteOrder(OrderDeletedEvent event) {
        sink.emitNext(event.getOrder(), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @EventListener
    public void onUpdateOrder(OrderUpdatedEvent event) {
        sink.emitNext(event.getOrder(), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    public Flux<Order> getPublisher() {
        return this.sink.asFlux();
    }
}

package com.example.demo.services;

import com.example.demo.persistance.entity.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

@Component
public class OrderPublisher {

    private final Sinks.Many<Order> sink;


    public OrderPublisher() {
        this.sink = Sinks.many()
                .multicast()
                .onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
    }

    public void updateSink(Order order) {
        sink.emitNext(order, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    public Flux<Order> getPublisher() {
        return this.sink.asFlux();
    }
}

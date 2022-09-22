package com.example.demo.services;

import com.example.demo.persistance.entity.Customer;
import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.entity.OrderItem;
import com.example.demo.persistance.entity.OrderItemPK;
import com.example.demo.persistance.repository.*;
import com.example.demo.utility.OrderInput;
import com.example.demo.utility.OrderItemInput;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final WarehouseService warehouseService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Order deleteOrder(BigInteger id) {
        
        Order deleteOrder = new Order();

        Optional<Order> findOrder = orderRepository.findById(id);

        if (findOrder.isPresent()) {
            deleteOrder = findOrder.get();
            orderRepository.deleteById(id);
        }

        crudLog(deleteOrder,"deleted");

        applicationEventPublisher.publishEvent(new OrderDeletedEvent(deleteOrder));

        return deleteOrder;
    }

    public Order createOrder(OrderInput input) {

        Order newOrder = buildFromInput(input);

        setSalesMan(newOrder, input);

        Optional<Customer> customer = customerRepository.findById(input.getCustomerId());

        if (customer.isEmpty())
            return newOrder;
        else
            newOrder.setCustomer(customer.get());

        setOrderItems(newOrder, input);

        newOrder = orderRepository.save(newOrder);

        crudLog(newOrder,"created");

        applicationEventPublisher.publishEvent(new OrderCreatedEvent(newOrder));

        return newOrder;
    }

    public Order updateOrder(OrderInput input) {

        Order updatedOrder = buildFromInput(input);

        Optional<Customer> customer;

        setSalesMan(updatedOrder, input);

        if (input.getCustomerId()!=null) {
            customer = customerRepository.findById(input.getCustomerId());

            if (customer.isEmpty())
                return updatedOrder;
            else
                updatedOrder.setCustomer(customer.get());
        }

        setOrderItems(updatedOrder, input);

        Order returnedOrder = orderRepository.save(updatedOrder);

        crudLog(returnedOrder,"updated");

        applicationEventPublisher.publishEvent(new OrderUpdatedEvent(updatedOrder,input));

        return returnedOrder;
    }

    private void setSalesMan(Order order, OrderInput input) {

        if (input.getSalesManId()!=null) {
            employeeRepository.findById(input.getSalesManId()).ifPresent(order::setSalesMan);
        }
    }

    public List<Order> getOrdersByDate(LocalDate date) {

        List<Order> orders = orderRepository.getOrderByOrderDate(date);

        return orders;
    }

    public List<Order> getOrdersByCustomer(BigInteger id) {

        List<Order> orders = orderRepository.findOrdersByCustomer_CustomerId(id);
        return orders;
    }

    public List<Order> getOrdersByProduct(BigInteger id) {

        //option one - via jpql
        //List<Order> orders = orderRepository.findOrdersByProduct(id);

        //option two - via jpaRepository naming convention
        List<Order> orders = orderItemRepository.getOrderItemsByProduct_ProductId(id)
                .stream().map(OrderItem::getOrder).distinct().collect(Collectors.toList());

        return orders;
    }

    private Order buildFromInput(OrderInput input) {
        return new Order(input.getOrderId(), input.getStatus(), input.getOrderDate(), null, null, null);
    }

    private void setOrderItems(Order newOrder, OrderInput input) {
        List<OrderItem> orderItems;
        OrderItem orderItem;

        if (input.getOrderItems()!=null) {

            orderItems = new ArrayList<>(input.getOrderItems().size());

            for (OrderItemInput orderItemInput : input.getOrderItems()) {

                orderItem = new OrderItem(new OrderItemPK(orderItemInput.getOrderId(),orderItemInput.getItemId()),
                        orderItemInput.getQuantity(), orderItemInput.getUnitPrice(), null, newOrder);

                productRepository.findById(orderItemInput.getProductId()).ifPresent(orderItem::setProduct);

                orderItems.add(orderItem);
            }
            newOrder.setOrderItems(orderItems);
        }
    }

    private void crudLog(Order order,String operation) {

        log.info("Order " + operation + " '{" + order.toString() +"}'");
    }
}

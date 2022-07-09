package com.example.demo.services;

import com.example.demo.persistance.entity.Customer;
import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.entity.OrderItem;
import com.example.demo.persistance.entity.OrderItemPK;
import com.example.demo.persistance.repository.*;
import com.example.demo.utility.OrderInput;
import com.example.demo.utility.OrderItemInput;
import lombok.AllArgsConstructor;
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

    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;


    public Order deleteOrder(BigInteger id) {
        
        Order deleteOrder = new Order();

        Optional<Order> findOrder = orderRepository.findById(id);

        if (findOrder.isPresent()) {
            deleteOrder = findOrder.get();
            orderRepository.deleteById(id);
        }

        return deleteOrder;
    }

    public Order createOrder(OrderInput input) {
        
        Order newOrder = fromInput(input);

        setSalesMan(input, newOrder);

        Optional<Customer> customer = customerRepository.findById(input.customerId());

        if (customer.isEmpty())
            return newOrder;
        else
            newOrder.setCustomer(customer.get());

        setOrderItems(input, newOrder);

        newOrder = orderRepository.save(newOrder);

        return newOrder;
    }

    public Order updateOrder(OrderInput input) {

        Order newOrder = fromInput(input);

        Optional<Customer> customer;

        setSalesMan(input, newOrder);

        if (input.customerId()!=null) {
            customer = customerRepository.findById(input.customerId());

            if (customer.isEmpty())
                return newOrder;
            else
                newOrder.setCustomer(customer.get());
        }

        setOrderItems(input, newOrder);

        return orderRepository.save(newOrder);
    }

    private void setSalesMan(OrderInput input, Order newOrder) {

        if (input.salesManId()!=null) {
            employeeRepository.findById(input.salesManId()).ifPresent(newOrder::setSalesMan);
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

    private Order fromInput(OrderInput input) {
        return new Order(input.orderId(), input.status(), input.orderDate(), null, null, null);
    }

    private void setOrderItems(OrderInput input, Order newOrder) {
        List<OrderItem> orderItems;
        OrderItem orderItem;

        if (input.orderItems()!=null) {

            orderItems = new ArrayList<>(input.orderItems().size());

            for (OrderItemInput orderItemInput : input.orderItems()) {

                orderItem = new OrderItem(new OrderItemPK(orderItemInput.orderId(),orderItemInput.itemId()),
                        orderItemInput.quantity(), orderItemInput.unitPrice(), null, newOrder);

                productRepository.findById(orderItemInput.productId()).ifPresent(orderItem::setProduct);

                orderItems.add(orderItem);
            }

            newOrder.setOrderItems(orderItems);
        }
    }
}

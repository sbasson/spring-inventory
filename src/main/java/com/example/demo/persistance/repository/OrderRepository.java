package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, BigInteger> {

    @Query( " select distinct o" +
            " from Order o" +
            " join o.orderItems oi" +
            " where oi.product.productId = :productId")
    List<Order> findOrdersByProduct(BigInteger productId);

    List<Order> findOrdersByCustomer_CustomerId(BigInteger customerId);

    List<Order> getOrderByOrderDate(LocalDate date);
}

package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Employee;
import com.example.demo.persistance.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, BigInteger> {

    @Query( " select o" +
            " from Order o" +
            " join fetch o.orderItems oi" +
            " where oi.product.productId = :productId")
    List<Order> findOrdersByProduct(BigInteger productId);

    List<Order> findOrdersByCustomer_CustomerId(BigInteger customerId);

    @Query( " select distinct e" +
            " from Employee e" +
            " join fetch e.orders o " +
            " where o.status = 'Pending'")
    List<Employee> salesMansOfPendingOrders();

    List<Order> getOrderByOrderDate(LocalDate date);
}

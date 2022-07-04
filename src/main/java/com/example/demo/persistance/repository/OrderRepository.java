package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, BigInteger> {

    @Query( " select o " +
            " from Order o" +
            " join fetch o.orderItems oi" +
            " where oi.product.productId = :productId")
    List<Order> findOrdersByProduct(@Param("productId") BigInteger productId);
}

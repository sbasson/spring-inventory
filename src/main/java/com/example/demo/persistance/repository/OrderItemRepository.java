package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.OrderItem;
import com.example.demo.persistance.entity.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

    List<OrderItem> getOrderItemsByProduct_ProductId(BigInteger productId);

}

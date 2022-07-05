package com.example.demo.controllers;


import com.example.demo.persistance.entity.Inventory;
import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.repository.CustomerRepository;
import com.example.demo.persistance.repository.InventoryRepository;
import com.example.demo.persistance.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Controller
@AllArgsConstructor
class Query {

    private final OrderRepository orderRepository;

    private final InventoryRepository inventoryRepository;

    @QueryMapping
    public List<Order> ordersByProduct(@Argument BigInteger id) {

        List<Order> orders = orderRepository.findOrdersByProduct(id);
        return orders;
    }

    @QueryMapping
    public List<Inventory> inventoriesByProduct(@Argument BigInteger id) {

        List<Inventory> inventories = inventoryRepository.getInventoryById_ProductIdAndAndQuantityGreaterThan(id,0);
        return inventories;
    }

    @QueryMapping
    public List<Order> ordersByCustomer(@Argument BigInteger id) {

        List<Order> orders = orderRepository.findOrdersByCustomer_CustomerId(id);
        return orders;
    }




}




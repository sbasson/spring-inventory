package com.example.demo.controllers;


import com.example.demo.persistance.entity.Inventory;
import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.repository.CustomerRepository;
import com.example.demo.persistance.repository.InventoryRepository;
import com.example.demo.persistance.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Controller
class Query {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @QueryMapping
    public List<Order> ordersByProduct(@Argument("id") BigInteger id) {

        List<Order> orders = orderRepository.findOrdersByProduct(id);
        return orders;
    }

    @QueryMapping
    public List<Inventory> inventoriesByProduct(@Argument("id") BigInteger id) {

        List<Inventory> inventories = inventoryRepository.getInventoryById_ProductIdAndAndQuantityGreaterThan(id,0);
        return inventories;
    }




}




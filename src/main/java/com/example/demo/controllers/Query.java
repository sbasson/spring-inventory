package com.example.demo.controllers;


import com.example.demo.persistance.entity.Employee;
import com.example.demo.persistance.entity.Inventory;
import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.repository.EmployeeRepository;
import com.example.demo.persistance.repository.InventoryRepository;
import com.example.demo.persistance.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
class Query {

    private final OrderRepository orderRepository;

    private final InventoryRepository inventoryRepository;

    private final EmployeeRepository employeeRepository;

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

    @QueryMapping
    public Iterable<Employee> pendingOrdersOfSalesMans() {

        //option one - filter in the app, more code, less readable, more runtime
//        List<Employee> all = employeeRepository.findAll();
//
//        Map<Employee, List<Order>> salesToOrderMap = all.stream()
//                .flatMap(employee -> employee.getOrders().stream())
//                .filter(order -> order.getStatus().equals("Pending"))
//                .collect(Collectors.groupingBy(Order::getSalesMan));
//
//        Set<Employee> pendingSalesMan = salesToOrderMap.keySet();
//
//        pendingSalesMan.forEach(employee -> employee.setOrders(salesToOrderMap.get(employee)));

        //option two - filter in the jpa via query, less code, more readable, less runtime
        List<Employee> pendingSalesMan = orderRepository.salesMansOfPendingOrders();
        return pendingSalesMan;
    }






}




package com.example.demo.controllers;


import com.example.demo.persistance.entity.*;
import com.example.demo.services.*;
import com.example.demo.utility.CountryProductsDTO;
import com.example.demo.utility.OrderInput;
import com.example.demo.utility.ProductInput;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
class GraphQLController {

    private final ProductService productService;
    private final WarehouseService warehouseService;
    private final OrderService orderService;
    private final EmployeeService employeeService;
    private final OrderListenerPublisher orderListenerPublisher;

    @QueryMapping
    public List<Order> ordersByProduct(@Argument BigInteger id) {

        return orderService.getOrdersByProduct(id);
    }

    @QueryMapping
    public List<Inventory> inventoriesByProduct(@Argument BigInteger id) {

        return warehouseService.getAvailableInventoriesByProduct(id);
    }

    @QueryMapping
    public List<Order> ordersByCustomer(@Argument BigInteger id) {

        return orderService.getOrdersByCustomer(id);
    }

    @QueryMapping
    public List<Employee> salesmanWithPendingOrders() {

        return employeeService.getSalesmanWithPendingOrders();
    }

    @QueryMapping
    public List<CountryProductsDTO> countriesWithTopFiveSellingProduct() {

        return productService.getCountriesWithTopFiveSellingProduct();
    }

    @QueryMapping
    public List<Warehouse> warehousesOutOfStockByProduct(@Argument BigInteger id) {

        return warehouseService.getWarehousesOutOfStockByProduct(id);
    }

    @QueryMapping
    public List<Order> ordersByDate(@Argument LocalDate date) {

        return orderService.getOrdersByDate(date);
    }

    @QueryMapping
    public List<Product> productsByCategory(@Argument BigInteger id) {

        return productService.getProductsByCategory(id);
    }

    @MutationMapping
    public Product deleteProduct(@Argument BigInteger id) {

        return productService.deleteProduct(id);
    }

    @MutationMapping
    public Product createProduct(@Argument ProductInput input) {

        return productService.createProduct(input);
    }

    @MutationMapping
    public Product updateProduct(@Argument ProductInput input) {

        return productService.updateProduct(input);
    }

    @MutationMapping
    public Warehouse deleteWarehouse(@Argument BigInteger id) {

        return warehouseService.deleteWarehouse(id);
    }

    @MutationMapping
    public Order deleteOrder(@Argument BigInteger id) {

        return orderService.deleteOrder(id);
    }

    @MutationMapping
    public Order createOrder(@Argument OrderInput input) {

        return orderService.createOrder(input);
    }

    @MutationMapping
    public Order updateOrder(@Argument OrderInput input) {

        return orderService.updateOrder(input);
    }

    @SubscriptionMapping
    public Flux<Order> onNewOrder() {
        return orderListenerPublisher.getPublisher();
    }

    @SubscriptionMapping
    public Flux<Order> onUpdateOrder() {
        return orderListenerPublisher.getPublisher();
    }

    @SubscriptionMapping
    public Flux<Order> onDeleteOrder() {
        return orderListenerPublisher.getPublisher();
    }

//    @QueryMapping
//    public int test(@Argument BigInteger productId, @Argument BigInteger warehouseId,@Argument int quantity) {
//
//        Order order = orderRepository.findById(BigInteger.ONE).get();
//        warehouseService.updateWarehouseByOrderCreated(order);
//        Inventory inventory = new Inventory(new InventoryPK(productId, warehouseId), quantity, null, null);
//        productRepository.findById(inventory.getId().getProductId()).ifPresent(inventory::setProduct);
//        warehouseRepository.findById(inventory.getId().getWarehouseId()).ifPresent(inventory::setWarehouse);
//        inventory = warehouseService.save(inventory);
//
//        return 1;
//    }

}




package com.example.demo.controllers;


import com.example.demo.persistance.entity.*;
import com.example.demo.persistance.repository.*;
import com.example.demo.utility.CountryProductsDTO;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
class Query {

    private final OrderRepository orderRepository;

    private final InventoryRepository inventoryRepository;

    private final EmployeeRepository employeeRepository;

    private final ProductRepository productRepository;

    private final CountryRepository countryRepository;

    @QueryMapping
    public List<Order> ordersByProduct(@Argument BigInteger id) {

        List<Order> orders = orderRepository.findOrdersByProduct(id);
        return orders;
    }

    @QueryMapping
    public List<Inventory> inventoriesByProduct(@Argument BigInteger id) {

        List<Inventory> inventories = inventoryRepository.getInventoriesById_ProductId(id)
                .stream().filter(i->i.getQuantity()>0).collect(Collectors.toList());

        return inventories;
    }

    @QueryMapping
    public List<Order> ordersByCustomer(@Argument BigInteger id) {

        List<Order> orders = orderRepository.findOrdersByCustomer_CustomerId(id);
        return orders;
    }

    @QueryMapping
    public List<Employee> salesmanWithPendingOrders() {

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

    @QueryMapping
    public List<CountryProductsDTO> countriesWithTopFiveSellingProduct() {

        List<Country> allCountries = countryRepository.findAll();

        List<Product> products;
        List<CountryProductsDTO> countryProductsDTO = new ArrayList<>();

        for (Country country : allCountries) {

            products = productRepository.topSellingProductsByCountry(country.getCountryId());

            if (products.size()>=5) {
                products = products.subList(0,5);
            }

            countryProductsDTO.add(new CountryProductsDTO(country,products));
        }

        return countryProductsDTO;
    }

    @QueryMapping
    public List<Warehouse> warehousesOutOfStockByProduct(@Argument BigInteger id) {

        List<Inventory> inventories = inventoryRepository.getInventoriesById_ProductId(id);

        List<Warehouse> warehouses = inventories.stream().filter(i->i.getQuantity()==0)
                .map(Inventory::getWarehouse).collect(Collectors.toList());

        return warehouses;
    }

    @QueryMapping
    public List<Order> ordersByDate(@Argument LocalDate date) {

        List<Order> orders = orderRepository.getOrderByOrderDate(date);

        return orders;
    }

    @QueryMapping
    public List<Product> productByCategory(@Argument BigInteger id) {

        List<Product> products = productRepository.findProductsByProductCategory_CategoryId(id);

        return products;
    }

}




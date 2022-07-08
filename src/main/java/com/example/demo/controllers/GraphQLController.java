package com.example.demo.controllers;


import com.example.demo.persistance.entity.*;
import com.example.demo.persistance.repository.*;
import com.example.demo.utility.CountryProductsDTO;
import com.example.demo.utility.OrderInput;
import com.example.demo.utility.ProductInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
class GraphQLController {

    private final OrderRepository orderRepository;

    private final InventoryRepository inventoryRepository;

    private final EmployeeRepository employeeRepository;

    private final ProductRepository productRepository;

    private final CountryRepository countryRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final WarehouseRepository warehouseRepository;

    private final CustomerRepository customerRepository;

    @QueryMapping
    public List<Order> ordersByProduct(@Argument BigInteger id) {

        //option one - via jpql
        //List<Order> orders = orderRepository.findOrdersByProduct(id);

        //option two - via jpaRepository naming convention
        List<Order> orders = orderItemRepository.getOrderItemsByProduct_ProductId(id)
                .stream().map(OrderItem::getOrder).distinct().collect(Collectors.toList());

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
        //List<Employee> all = employeeRepository.findAll();
        //
        //Map<Employee, List<Order>> salesToOrderMap = all.stream()
        //        .flatMap(employee -> employee.getOrders().stream())
        //        .filter(order -> order.getStatus().equals("Pending"))
        //        .collect(Collectors.groupingBy(Order::getSalesMan));
        //
        //Set<Employee> pendingSalesMan = salesToOrderMap.keySet();
        //
        //pendingSalesMan.forEach(employee -> employee.setOrders(salesToOrderMap.get(employee)));

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

            products = productRepository.topSellingProductsByCountry(country.getCountryId(), PageRequest.of(0, 5));

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

    @MutationMapping
    public Product deleteProduct(@Argument BigInteger id) {

        Product deleteProduct = new Product();

        Optional<Product> findProduct = productRepository.findById(id);

        if (findProduct.isPresent()) {
            deleteProduct = findProduct.get();
            productRepository.deleteById(id);
        }

        return deleteProduct;
    }

    @MutationMapping
    public Product createProduct(@Argument ProductInput input) {

        Product newProduct = new Product(null,input.productName(),input.description(),
                input.standardCost(),input.listPrice());

        Optional<ProductCategory> productCategory = productCategoryRepository.findById(input.productCategoryId());

        if (productCategory.isEmpty()) {
            return newProduct;
        }

        newProduct.setProductCategory(productCategory.get());

        return productRepository.save(newProduct);
    }

    @MutationMapping
    public Product updateProduct(@Argument ProductInput input) {

        Product updateProduct = new Product(input.productId(),input.productName(),input.description(),
                input.standardCost(),input.listPrice());

        Optional<ProductCategory> productCategory;

        if (input.productCategoryId()!=null) {
            productCategory = productCategoryRepository.findById(input.productCategoryId());

            if (productCategory.isEmpty())
                return updateProduct;
            else
                updateProduct.setProductCategory(productCategory.get());
        }

        return productRepository.save(updateProduct);
    }

    @MutationMapping
    public Warehouse deleteWarehouse(@Argument BigInteger id) {

        Warehouse deleteWarehouse = new Warehouse();

        Optional<Warehouse> findWarehouse = warehouseRepository.findById(id);

        if (findWarehouse.isPresent()) {
            deleteWarehouse = findWarehouse.get();
            warehouseRepository.deleteById(id);
        }

        return deleteWarehouse;
    }

    @MutationMapping
    public Order deleteOrder(@Argument BigInteger id) {

        Order deleteOrder = new Order();

        Optional<Order> findOrder = orderRepository.findById(id);

        if (findOrder.isPresent()) {
            deleteOrder = findOrder.get();
            orderRepository.deleteById(id);
        }

        return deleteOrder;
    }

    @MutationMapping
    public Order createOrder(@Argument OrderInput input) {

        Order newOrder = new Order(null, input.status(), input.orderDate(),null,null,null);

        Optional<Customer> customer = customerRepository.findById(input.customerId());
        Optional<Employee> salesMan;

        if (input.salesManId()!=null) {
            salesMan = employeeRepository.findById(input.salesManId());
            salesMan.ifPresent(newOrder::setSalesMan);
        }

        if (customer.isEmpty())
            return newOrder;
        else
            newOrder.setCustomer(customer.get());

        return orderRepository.save(newOrder);
    }

    @MutationMapping
    public Order updateOrder(@Argument OrderInput input) {

        Order newOrder = new Order(input.orderId(), input.status(), input.orderDate(),null,null,null);

        Optional<Customer> customer;
        Optional<Employee> salesMan;

        if (input.salesManId()!=null) {
            salesMan = employeeRepository.findById(input.salesManId());
            salesMan.ifPresent(newOrder::setSalesMan);
        }

        if (input.customerId()!=null) {
            customer = customerRepository.findById(input.customerId());

            if (customer.isEmpty())
                return newOrder;
            else
                newOrder.setCustomer(customer.get());
        }

        return orderRepository.save(newOrder);
    }
}




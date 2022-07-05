package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, BigInteger> {

        @Query( " select new Product (oi.product.productId,oi.product.productName,oi.product.description,oi.product.standardCost,oi.product.listPrice) " +
                " from OrderItem oi" +
                " join oi.product.inventories i" +
                " where oi.order.status <> 'Canceled'" +
                " and i.warehouse.location.country.countryId = :countryId" +
                " group by oi.product.productId,oi.product.productName,oi.product.description,oi.product.standardCost,oi.product.listPrice" +
                " order by sum(oi.quantity) desc")
        List<Product> topSellingProductsByCountry(String countryId);
}

package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, BigInteger> {

}

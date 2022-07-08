package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, BigInteger> {
}

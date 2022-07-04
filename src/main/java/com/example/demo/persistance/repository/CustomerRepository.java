package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CustomerRepository extends JpaRepository<Customer, BigInteger> {


}

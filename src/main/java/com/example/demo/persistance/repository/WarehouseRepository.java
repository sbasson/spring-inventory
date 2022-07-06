package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, BigInteger> {

}

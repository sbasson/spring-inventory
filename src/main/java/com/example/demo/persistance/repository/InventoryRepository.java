package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Inventory;
import com.example.demo.persistance.entity.InventoryPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryPK> {

    List<Inventory> getInventoryById_ProductIdAndAndQuantityGreaterThan(BigInteger productId, int amount);
}

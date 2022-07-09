package com.example.demo.services;

import com.example.demo.persistance.entity.Inventory;
import com.example.demo.persistance.entity.Warehouse;
import com.example.demo.persistance.repository.InventoryRepository;
import com.example.demo.persistance.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;


    public Warehouse deleteWarehouse(BigInteger id) {

        Warehouse deleteWarehouse = new Warehouse();

        Optional<Warehouse> findWarehouse = warehouseRepository.findById(id);

        if (findWarehouse.isPresent()) {
            deleteWarehouse = findWarehouse.get();
            warehouseRepository.deleteById(id);
        }

        return deleteWarehouse;
    }

    public List<Warehouse> getWarehousesOutOfStockByProduct(BigInteger id) {

        List<Inventory> inventories = inventoryRepository.getInventoriesById_ProductId(id);

        List<Warehouse> warehouses = inventories.stream().filter(i->i.getQuantity()==0)
                .map(Inventory::getWarehouse).collect(Collectors.toList());

        return warehouses;
    }

    public List<Inventory> getInventoriesByProduct(BigInteger id) {

        List<Inventory> inventories = inventoryRepository.getInventoriesById_ProductId(id)
                .stream().filter(i->i.getQuantity()>0).collect(Collectors.toList());

        return inventories;
    }

    public void updateInventory() {

    }
}

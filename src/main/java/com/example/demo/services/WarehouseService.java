package com.example.demo.services;

import com.example.demo.persistance.entity.*;
import com.example.demo.persistance.repository.InventoryRepository;
import com.example.demo.persistance.repository.ProductRepository;
import com.example.demo.persistance.repository.WarehouseRepository;
import com.example.demo.utility.OrderInput;
import com.example.demo.utility.OrderItemInput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;


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

    public List<Inventory> getAvailableInventoriesByProduct(BigInteger id) {

        List<Inventory> inventories = inventoryRepository.getInventoriesById_ProductId(id)
                .stream().filter(i->i.getQuantity()>0).collect(Collectors.toList());

        return inventories;
    }

    public void updateWarehouseOrderDeleted(Order order) {

        if (order.getOrderItems()==null) return;
        //for each order item - add the amount of product to some inventory of the particular product
        //or create one if not exists
        for (OrderItem orderItem : order.getOrderItems()) {
            addInventoryOf(orderItem.getProduct().getProductId(), orderItem.getQuantity());
        }
    }

    public void updateWarehouseOrderCreated(Order order) {

        //for each order item  go over his inventories and reduce
        for (OrderItem orderItem : order.getOrderItems()) {
            reduceFromInventoriesOf(orderItem.getProduct().getProductId(), orderItem.getQuantity());
        }
    }

    public void updateWarehouseOrderUpdated(Order order, OrderInput orderInput) {

        int diff;

        //for each order item  go over his inventories and find the difference between old
        //quantity to new and update by the result
        for (OrderItemInput orderItemInput : orderInput.orderItems()) {

            Optional<OrderItem> orderItem = order.getOrderItems().stream()
                    .filter(oi -> oi.getId().orderId.equals(orderItemInput.orderId()))
                    .findFirst();

            if (orderItem.isPresent())
                diff = orderItemInput.quantity() - orderItem.get().getQuantity();
            else
                diff = orderItemInput.quantity();

            if (diff>0) //the new amount was bigger, increasing.
                addInventoryOf(orderItemInput.productId(), diff);
            else //the new amount was smaller, decreasing.
                reduceFromInventoriesOf(orderItemInput.productId(),Math.abs(diff));
        }
    }

    private void reduceFromInventoriesOf(BigInteger productId, int quantityToReduce) {

        Iterator<Inventory> iterator;
        Inventory availableInventory;
        List<Inventory> availableInventories;

        availableInventories = getAvailableInventoriesByProduct(productId);

        iterator = availableInventories.iterator();

        while (quantityToReduce > 0 && iterator.hasNext()) {

            availableInventory = iterator.next();
            int quantity = availableInventory.getQuantity();
            availableInventory.setQuantity(Math.max(quantity - quantityToReduce, 0));
            quantityToReduce =  quantityToReduce - quantity;
            inventoryRepository.save(availableInventory);
        }
    }

    private void addInventoryOf(BigInteger productId, int quantity) {

        Inventory inventory;
        List<Inventory> inventories;

        inventories = inventoryRepository.getInventoriesById_ProductId(productId);

        if (inventories.isEmpty())
            inventory = new Inventory(new InventoryPK(productId,BigInteger.ONE),0,null,null);
        else
            inventory = inventories.get(0);

        inventory.setQuantity(inventory.getQuantity()+ quantity);
        productRepository.findById(inventory.getId().getProductId()).ifPresent(inventory::setProduct);
        warehouseRepository.findById(inventory.getId().getWarehouseId()).ifPresent(inventory::setWarehouse);

        inventoryRepository.save(inventory);
    }
}

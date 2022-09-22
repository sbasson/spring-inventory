package com.example.demo.services;

import com.example.demo.persistance.entity.*;
import com.example.demo.persistance.repository.InventoryRepository;
import com.example.demo.persistance.repository.WarehouseRepository;
import com.example.demo.utility.OrderInput;
import com.example.demo.utility.OrderItemInput;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarehouseService {

    private static final Logger log = LoggerFactory.getLogger(WarehouseService.class);

    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductService productService;


    public Warehouse deleteWarehouse(BigInteger id) {

        Warehouse deleteWarehouse = new Warehouse();

        Optional<Warehouse> findWarehouse = warehouseRepository.findById(id);

        if (findWarehouse.isPresent()) {
            deleteWarehouse = findWarehouse.get();
            warehouseRepository.deleteById(id);

            log.info("Warehouse deleted '{" +
                    "warehouseId = " + deleteWarehouse.getWarehouseId() + "\n" +
                    ((deleteWarehouse.getWarehouseName()!=null)? ("warehouseName = " + deleteWarehouse.getWarehouseName() + "\n"):"")+
                    ((deleteWarehouse.getLocation()!=null)? ("locationId = " + deleteWarehouse.getLocation().getLocationId() + "\n"):"")+
                    "}'");
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

    public void updateWarehouseByOrderDeleted(Order order) {

        if (order.getOrderItems()==null) return;

        //for each order item - add the amount of product to some inventory of the particular product
        //or create one if not exists
        for (OrderItem orderItem : order.getOrderItems()) {
            addInventoryOf(orderItem.getProduct().getProductId(), orderItem.getQuantity());
        }
    }

    public void updateWarehouseByOrderCreated(Order order) {

        if (order.getOrderItems()==null) return;

        //for each order item  go over his inventories and reduce
        for (OrderItem orderItem : order.getOrderItems()) {
            reduceFromInventoriesOf(orderItem.getProduct().getProductId(), orderItem.getQuantity());
        }
    }

    public void updateWarehouseByOrderUpdated(Order order, OrderInput orderInput) {

        //we compare between old order items with new order items from input
        int diff;

        //for each order item input we search him in the order items list
        //if it exists we update the quantity as the subtraction result
        //if not we update the whole quantity
        for (OrderItemInput orderItemInput : orderInput.getOrderItems()) {

            Optional<OrderItem> orderItem = order.getOrderItems().stream()
                    .filter(oi -> oi.getId().orderId.equals(orderItemInput.getOrderId()))
                    .findFirst();

            if (orderItem.isPresent())
                diff = orderItemInput.getQuantity() - orderItem.get().getQuantity();
            else
                diff = orderItemInput.getQuantity();

            if (diff<0) //the new amount was smaller, increasing from inventory
                addInventoryOf(orderItemInput.getProductId(), Math.abs(diff));
            else //the new amount was bigger, decreasing
                reduceFromInventoriesOf(orderItemInput.getProductId(),diff);
        }
    }

    public void reduceFromInventoriesOf(BigInteger productId, int quantityToReduce) {

        Iterator<Inventory> iterator;
        Inventory availableInventory;
        List<Inventory> availableInventories;
        int quantity;

        availableInventories = getAvailableInventoriesByProduct(productId);

        iterator = availableInventories.iterator();

        while (quantityToReduce > 0 && iterator.hasNext()) {

            availableInventory = iterator.next();
            quantity = availableInventory.getQuantity();
            availableInventory.setQuantity(Math.max(quantity - quantityToReduce, 0));
            quantityToReduce =  quantityToReduce - quantity;

            inventoryRepository.save(availableInventory);
        }
    }

    public void addInventoryOf(BigInteger productId, int quantity) {

        List<Inventory> inventories;
        Inventory inventory;
        Warehouse warehouse;

        inventories = inventoryRepository.getInventoriesById_ProductId(productId);

        if (inventories.isEmpty()) {
            warehouse = warehouseRepository.findAll().get(0); //choose arbitrary warehouse
            inventory = buildInventory(productId, warehouse.getWarehouseId());
        }
        else {
            inventory = inventories.get(0);
        }

        inventory.setQuantity(inventory.getQuantity() + quantity);

        inventoryRepository.save(inventory);
    }

    public Inventory buildInventory(BigInteger productId, BigInteger warehouseId) {

        Warehouse warehouse = buildWarehouse(warehouseId);
        Product product = productService.buildProduct(productId);

        return new Inventory(new InventoryPK(productId, warehouseId), 0, product, warehouse);
    }

    public Warehouse buildWarehouse(BigInteger warehouseId) {
        return new Warehouse(warehouseId);
    }
}

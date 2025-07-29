package com.E_Commerce_Microservices.inventory_service.service;

import com.E_Commerce_Microservices.inventory_service.entity.Inventory;
import com.E_Commerce_Microservices.inventory_service.entity.Product;
import com.E_Commerce_Microservices.inventory_service.repository.InventoryRepository;
import com.E_Commerce_Microservices.inventory_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory addProduct(Long id, int quantity) {
        Optional<Inventory> checkProduct = inventoryRepository.findById(id);
        if (checkProduct.isPresent()) {
            throw new RuntimeException("product with id : " + id + " already exists");
        }
        Product product = productRepository.findById(id).get();
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(quantity);

        inventoryRepository.save(inventory);

        return inventory;
    }

    public void updateStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);
    }

    public void reduceStock(Long productId, int amount) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));



        inventory.setQuantity(inventory.getQuantity() - amount);
        inventoryRepository.save(inventory);
    }

    public int getStock(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        return inventory.getQuantity();
    }
    public List<Inventory> getAllInventorys() {
        return inventoryRepository.findAll();
    }
    public void deleteInventory(Long Id) {
        inventoryRepository.deleteById(Id);
    }
}

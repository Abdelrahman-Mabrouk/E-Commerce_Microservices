package com.E_Commerce_Microservices.inventory_service.controller;

import com.E_Commerce_Microservices.inventory_service.entity.Inventory;
import com.E_Commerce_Microservices.inventory_service.entity.Product;
import com.E_Commerce_Microservices.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;


    @PostMapping("/addProduct/{id}")
    public ResponseEntity<Inventory> addProduct(@PathVariable long id,@RequestParam int quantity) {

        return ResponseEntity.ok(inventoryService.addProduct(id, quantity));
    }

    @PutMapping("/stock/update/{productId}")
    public ResponseEntity<String> updateStock(@PathVariable Long productId, @RequestParam int quantity) {
        inventoryService.updateStock(productId, quantity);
        return ResponseEntity.ok("Stock updated");
    }

    @PutMapping("/stock/reduce/{productId}")
    public ResponseEntity<String> reduceStock(@PathVariable Long productId, @RequestParam int quantity) {
        inventoryService.reduceStock(productId, quantity);
        return ResponseEntity.ok("Stock reduced");
    }

    @GetMapping("/stock/{productId}")
    public int getStock(@PathVariable Long productId) {
        int quantity = inventoryService.getStock(productId);
        return quantity;
    }
    @GetMapping("/getAll")
    public List<Inventory> getAll() {
        return   inventoryService.getAllInventorys();

    }
    @DeleteMapping
    public ResponseEntity<?> deleteInventory(@RequestParam long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok("Inventory deleted");
    }

}

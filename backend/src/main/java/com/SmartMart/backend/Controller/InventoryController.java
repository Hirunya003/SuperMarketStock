package com.SmartMart.backend.Controller;

import com.SmartMart.backend.model.Inventory;
import com.SmartMart.backend.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllItems() {
        return inventoryService.getAllItems();
    }

    @GetMapping("/{id}")
    public Optional<Inventory> getItemById(@PathVariable String id) {
        return inventoryService.getItemById(id);
    }

    @PostMapping
    public Inventory addItem(@RequestBody Inventory item) {
        return inventoryService.addItem(item);
    }

    @PutMapping("/{id}")
    public Inventory updateItem(@PathVariable String id, @RequestBody Inventory updatedItem) {
        return inventoryService.updateItem(id, updatedItem);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        inventoryService.deleteItem(id);
    }
}
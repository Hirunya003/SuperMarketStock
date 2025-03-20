package com.SmartMart.backend.Supplier.controller;

import com.SmartMart.backend.Supplier.model.InventoryItem;
import com.SmartMart.backend.Supplier.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public InventoryItem addItem(@RequestBody InventoryItem item) {
        return inventoryService.addItem(item);
    }

    @GetMapping("/check-low-stock")
    public String checkLowStock() {
        inventoryService.checkLowStock();
        return "Low stock check completed";
    }
}

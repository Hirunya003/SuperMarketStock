package com.store.inventory_management.controller;

import com.store.inventory_management.dto.InventoryDTO;
import com.store.inventory_management.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<InventoryDTO> addInventory(@RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO savedInventory = inventoryService.addInventory(inventoryDTO);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{productCode}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable String productCode, @RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO updatedInventory = inventoryService.updateInventory(productCode, inventoryDTO);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/delete/{productCode}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String productCode) {
        inventoryService.deleteInventory(productCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    public ResponseEntity<List<InventoryDTO>> getAllInventory() {
        List<InventoryDTO> inventoryList = inventoryService.getAllInventory();
        return ResponseEntity.ok(inventoryList);
    }
}
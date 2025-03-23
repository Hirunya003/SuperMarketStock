package com.SmartMart.backend.Inventory.service;

import com.SmartMart.backend.model.Inventory;
import com.SmartMart.backend.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getItemById(String id) {
        return inventoryRepository.findById(id);
    }

    public Inventory addItem(Inventory item) {
        return inventoryRepository.save(item);
    }

    public Inventory updateItem(String id, Inventory updatedItem) {
        if (inventoryRepository.existsById(id)) {
            updatedItem.setId(id);
            return inventoryRepository.save(updatedItem);
        }
        return null;
    }

    public void deleteItem(String id) {
        inventoryRepository.deleteById(id);
    }
}

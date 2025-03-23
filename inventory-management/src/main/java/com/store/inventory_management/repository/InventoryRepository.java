package com.store.inventory_management.repository;

import com.store.inventory_management.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    boolean existsByProductCode(String productCode);
    Inventory findByProductCode(String productCode);
}
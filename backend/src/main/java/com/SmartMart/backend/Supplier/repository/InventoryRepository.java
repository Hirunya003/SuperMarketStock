package com.SmartMart.backend.Supplier.repository;

import com.SmartMart.backend.Supplier.model.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<InventoryItem, String> {
}
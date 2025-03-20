package com.SmartMart.backend.repository;

import com.SmartMart.backend.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    List<Inventory> findByCategory(String category); // Example query
}

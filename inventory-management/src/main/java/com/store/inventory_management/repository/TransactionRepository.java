package com.store.inventory_management.repository;

import com.store.inventory_management.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByStatus(String status);
}
package com.SmartMart.backend.Supplier.repository;

import com.SmartMart.backend.Supplier.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
}

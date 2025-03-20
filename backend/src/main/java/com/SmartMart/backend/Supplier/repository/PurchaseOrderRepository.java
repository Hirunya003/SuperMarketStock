package com.SmartMart.backend.Supplier.repository;

import com.SmartMart.backend.Supplier.model.PurchaseOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseOrderRepository extends MongoRepository<PurchaseOrder, String> {
}

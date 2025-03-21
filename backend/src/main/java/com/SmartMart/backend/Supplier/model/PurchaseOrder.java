package com.SmartMart.backend.Supplier.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "purchase_orders")
public class PurchaseOrder {
    @Id
    private String id;
    private String supplierId;
    private List<String> itemIds;
    private String status; // e.g., "Pending", "Dispatched", "Delivered"
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
}


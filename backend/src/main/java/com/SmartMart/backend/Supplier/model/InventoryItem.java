package com.SmartMart.backend.Supplier.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "inventory")
public class InventoryItem {
    @Id
    private String id;
    private String name;
    private int stockLevel;
    private int lowStockThreshold;
}

package com.SmartMart.backend.Supplier.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "inventory")
public class Inventory {

    @Id
    private String id;
    private String productName;
    private int quantity;
    private int reorderLevel; // Alert threshold

    public Inventory(String productName, int quantity, int reorderLevel) {
        this.productName = productName;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
    }
}


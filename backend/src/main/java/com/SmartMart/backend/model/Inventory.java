package com.SmartMart.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
@Data
public class Inventory {
    @Id
    private String id;
    private String productName;
    private String category;
    private int quantity;
    private double price;
    private String supplier;
    private boolean perishable;
    private String expirationDate; // Store as String for simplicity
}

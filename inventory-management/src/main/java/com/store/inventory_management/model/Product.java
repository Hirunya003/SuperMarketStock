package com.store.inventory_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products") // MongoDB collection name
@Data // Lombok: Generates getters, setters, toString, etc.
@NoArgsConstructor // Lombok: No-args constructor
@AllArgsConstructor // Lombok: All-args constructor
public class Product {

    @Id
    private String id; // MongoDB auto-generated ID

    private String productCode; // Unique code for the product
    private String productName; // Name of the product
    private String category;   // Product category (e.g., electronics, groceries)
    private int quantity;      // Stock quantity
    private double sellingPrice; // Price at which the product is sold
    private double costPrice;    // Price at which the product was acquired
}

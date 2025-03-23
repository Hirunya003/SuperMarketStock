package com.store.inventory_management.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document(collection = "inventory")
@Data // Lombok: Generates getters, setters, toString, etc.
@NoArgsConstructor // Lombok: No-args constructor
@AllArgsConstructor // Lombok: All-args constructor
public class Inventory {
    @Id
    private String id;
    private String productCode;
    private String productName;
    private String category;
    private int quantity;
    private double sellingPrice;
    private double costPrice;
}

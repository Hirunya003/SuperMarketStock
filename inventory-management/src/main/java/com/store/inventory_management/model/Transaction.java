package com.store.inventory_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions") // MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private String id; // MongoDB auto-generated ID

    private String productCode; // Links to the product in inventory
    private String productName; // Name of the product (for reference)
    private int quantity;       // Number of items in the transaction
    private double profit;      // Calculated profit: (sellingPrice - costPrice) * quantity
    private String status;      // Transaction status: "pending", "accepted", "rejected"

    // Optional: Add timestamps if you want to track when the transaction was created/updated
    private String createdBy;   // Store manager who created the transaction (e.g., email)
}

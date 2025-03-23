package com.store.inventory_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users") // MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id; // MongoDB auto-generated ID

    private String email;         // User's email (used for login)
    private String contact;       // Contact info (for cashiers)
    private String cashierUsername; // Username (optional for cashiers)
    private String password;      // Encrypted password
    private String role;          // Role: "CASHIER", "MANAGER", "ADMIN"

    // Optional: Add fields for cashier_code if needed for cashier management
    private String cashierCode;   // Unique code for cashiers (managed by admin)
}

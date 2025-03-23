package com.store.inventory_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String id;             // User ID
    private String email;          // User's email
    private String contact;        // Contact info (for cashiers)
    private String cashierUsername;// Cashier username
    private String password;       // Password (included in requests, excluded in responses)
    private String role;           // Role: "ROLE_CASHIER", "ROLE_MANAGER", "ROLE_ADMIN"
    private String cashierCode;    // Unique cashier code (for admin management)
}

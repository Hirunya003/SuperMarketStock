package com.SmartMart.backend.Supplier.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "suppliers")
public class Supplier {
    @Id
    private String id;
    private String name;
    private String contactDetails;
    private List<String> orderHistory; // List of order IDs
    private double performanceScore; // Based on delivery time, quality, etc.
}

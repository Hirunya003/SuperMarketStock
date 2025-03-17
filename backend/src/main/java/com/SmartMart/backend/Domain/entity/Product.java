package com.SmartMart.backend.Domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")//map to MongoDB collection
@Data //this take care of all getters & setters and two string methods
@AllArgsConstructor //
@NoArgsConstructor
public class Product {

    @Id //use to framework know this property should be treated as the unique identifier for each product inside the DB
    private ObjectId pId;
    private String name;
    private String category;
    private String subcategory;
    private double price;
    private int quantity;
    private String description;
    private String availability;



}

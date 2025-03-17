package com.SmartMart.backend.Domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    private String productId;
    private int quantity;
    private double price; //price per unit at the purchase time


}

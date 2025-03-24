package com.store.inventory_management.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private String id;
    private String productCode;
    private String productName;
    private Integer quantity;
    private Double profit;
    private String status;
    private String createdBy;
}
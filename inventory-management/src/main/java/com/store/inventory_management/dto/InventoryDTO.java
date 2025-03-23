package com.store.inventory_management.dto;

import lombok.Data;

@Data
public class InventoryDTO {

    private String id;
    private String productCode;
    private String productName;
    private String category;
    private Integer quantity;
    private Double sellingPrice;
    private Double costPrice;
}
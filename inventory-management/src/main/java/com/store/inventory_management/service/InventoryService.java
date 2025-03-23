package com.store.inventory_management.service;

import com.store.inventory_management.dto.InventoryDTO;
import com.store.inventory_management.model.Inventory;
import com.store.inventory_management.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryDTO addInventory(InventoryDTO inventoryDTO) {
        if (inventoryRepository.existsByProductCode(inventoryDTO.getProductCode())) {
            throw new IllegalArgumentException("Product code already exists");
        }
        Inventory inventory = mapToEntity(inventoryDTO);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return mapToDTO(savedInventory);
    }

    public InventoryDTO updateInventory(String productCode, InventoryDTO inventoryDTO) {
        Inventory existing = inventoryRepository.findByProductCode(productCode);
        if (existing == null) {
            throw new IllegalArgumentException("Inventory item not found");
        }
        existing.setProductName(inventoryDTO.getProductName());
        existing.setCategory(inventoryDTO.getCategory());
        existing.setQuantity(inventoryDTO.getQuantity());
        existing.setSellingPrice(inventoryDTO.getSellingPrice());
        existing.setCostPrice(inventoryDTO.getCostPrice());
        Inventory updatedInventory = inventoryRepository.save(existing);
        return mapToDTO(updatedInventory);
    }

    public void deleteInventory(String productCode) {
        Inventory existing = inventoryRepository.findByProductCode(productCode);
        if (existing == null) {
            throw new IllegalArgumentException("Inventory item not found");
        }
        inventoryRepository.delete(existing);
    }

    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Inventory getInventoryByProductCode(String productCode) {
        Inventory inventory = inventoryRepository.findByProductCode(productCode);
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory item not found");
        }
        return inventory;
    }

    public void updateInventoryQuantity(String productCode, int quantityChange) {
        Inventory inventory = inventoryRepository.findByProductCode(productCode);
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory item not found");
        }
        int newQuantity = inventory.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient inventory quantity");
        }
        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);
    }

    private InventoryDTO mapToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inventory.getId());
        dto.setProductCode(inventory.getProductCode());
        dto.setProductName(inventory.getProductName());
        dto.setCategory(inventory.getCategory());
        dto.setQuantity(inventory.getQuantity());
        dto.setSellingPrice(inventory.getSellingPrice());
        dto.setCostPrice(inventory.getCostPrice());
        return dto;
    }

    private Inventory mapToEntity(InventoryDTO dto) {
        Inventory inventory = new Inventory();
        inventory.setProductCode(dto.getProductCode());
        inventory.setProductName(dto.getProductName());
        inventory.setCategory(dto.getCategory());
        inventory.setQuantity(dto.getQuantity());
        inventory.setSellingPrice(dto.getSellingPrice());
        inventory.setCostPrice(dto.getCostPrice());
        return inventory;
    }
}
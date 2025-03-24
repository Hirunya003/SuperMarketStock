package com.store.inventory_management.service;

import com.store.inventory_management.dto.InventoryDTO;
import com.store.inventory_management.model.Inventory;
import com.store.inventory_management.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryDTO addInventory(InventoryDTO inventoryDTO) {
        // Check if manager is logged in
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!currentUserRole.contains("ROLE_MANAGER")) {
            throw new IllegalStateException("Only managers can add inventory");
        }

        // Enforce quantity limits (min 0, max 100)
        int quantity = inventoryDTO.getQuantity();
        if (quantity < 0 || quantity > 100) {
            throw new IllegalArgumentException("Quantity must be between 0 and 100");
        }

        // Check for duplicate product code and increase quantity if exists
        Inventory existing = inventoryRepository.findByProductCode(inventoryDTO.getProductCode());
        if (existing != null) {
            int newQuantity = existing.getQuantity() + quantity;
            if (newQuantity > 100) {
                throw new IllegalArgumentException("Total quantity cannot exceed 100");
            }
            existing.setQuantity(newQuantity);
            Inventory updatedInventory = inventoryRepository.save(existing);
            return mapToDTO(updatedInventory);
        }

        // Add new inventory item if no duplicate
        Inventory inventory = mapToEntity(inventoryDTO);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return mapToDTO(savedInventory);
    }

    public InventoryDTO updateInventory(String productCode, InventoryDTO inventoryDTO) {
        // Check if manager is logged in
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!currentUserRole.contains("ROLE_MANAGER")) {
            throw new IllegalStateException("Only managers can update inventory");
        }

        Inventory existing = inventoryRepository.findByProductCode(productCode);
        if (existing == null) {
            throw new IllegalArgumentException("Inventory item not found");
        }

        // Enforce quantity limits (min 0, max 100)
        int quantity = inventoryDTO.getQuantity();
        if (quantity < 0 || quantity > 100) {
            throw new IllegalArgumentException("Quantity must be between 0 and 100");
        }

        existing.setProductName(inventoryDTO.getProductName());
        existing.setCategory(inventoryDTO.getCategory());
        existing.setQuantity(quantity);
        existing.setSellingPrice(inventoryDTO.getSellingPrice());
        existing.setCostPrice(inventoryDTO.getCostPrice());
        Inventory updatedInventory = inventoryRepository.save(existing);
        return mapToDTO(updatedInventory);
    }

    public void deleteInventory(String productCode) {
        // Check if manager is logged in
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!currentUserRole.contains("ROLE_MANAGER")) {
            throw new IllegalStateException("Only managers can delete inventory");
        }

        Inventory existing = inventoryRepository.findByProductCode(productCode);
        if (existing == null) {
            throw new IllegalArgumentException("Inventory item not found");
        }
        inventoryRepository.delete(existing);
    }

    public List<InventoryDTO> getAllInventory() {
        // Display all inventory items with their current quantity levels
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
        if (newQuantity < 0 || newQuantity > 100) {
            throw new IllegalArgumentException("Quantity must be between 0 and 100");
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
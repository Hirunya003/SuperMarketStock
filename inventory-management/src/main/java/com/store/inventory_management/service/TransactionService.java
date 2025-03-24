package com.store.inventory_management.service;

import com.store.inventory_management.dto.TransactionDTO;
import com.store.inventory_management.model.Inventory; // Added for clarity
import com.store.inventory_management.model.Transaction; // Line 5: Import Transaction
import com.store.inventory_management.repository.TransactionRepository; // Line 6: Import TransactionRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository; // Line 16: Declaration

    @Autowired
    private InventoryService inventoryService; // Line 18: Declaration

    public TransactionDTO createTransaction(String productCode, int quantity, String managerEmail) {
        // Check if manager is logged in
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!currentUserRole.contains("ROLE_MANAGER")) {
            throw new IllegalStateException("Only managers can create transactions");
        }

        Inventory inventory = inventoryService.getInventoryByProductCode(productCode);
        if (inventory.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient inventory quantity");
        }

        // Calculate profit: (sellingPrice - costPrice) * quantity
        double profit = (inventory.getSellingPrice() - inventory.getCostPrice()) * quantity;

        Transaction transaction = new Transaction(); // Line 33: First use of Transaction
        transaction.setProductCode(productCode);
        transaction.setProductName(inventory.getProductName());
        transaction.setQuantity(quantity);
        transaction.setProfit(profit);
        transaction.setStatus("pending");
        transaction.setCreatedBy(managerEmail);

        Transaction savedTransaction = transactionRepository.save(transaction); // Line 40: Use of TransactionRepository and Transaction
        return mapToDTO(savedTransaction);
    }

    public TransactionDTO acceptTransaction(String transactionId) {
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!currentUserRole.contains("ROLE_CASHIER") && !currentUserRole.contains("ROLE_MANAGER")) {
            throw new IllegalStateException("Only cashiers or managers can accept transactions");
        }

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        if (!"pending".equals(transaction.getStatus())) {
            throw new IllegalArgumentException("Only pending transactions can be accepted");
        }

        inventoryService.updateInventoryQuantity(transaction.getProductCode(), -transaction.getQuantity());

        transaction.setStatus("accepted");
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return mapToDTO(updatedTransaction);
    }

    public TransactionDTO rejectTransaction(String transactionId) {
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!currentUserRole.contains("ROLE_CASHIER") && !currentUserRole.contains("ROLE_MANAGER")) {
            throw new IllegalStateException("Only cashiers or managers can reject transactions");
        }

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        if (!"pending".equals(transaction.getStatus())) {
            throw new IllegalArgumentException("Only pending transactions can be rejected");
        }

        transaction.setStatus("rejected");
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return mapToDTO(updatedTransaction);
    }

    public void deleteTransaction(String transactionId) {
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!currentUserRole.contains("ROLE_CASHIER") && !currentUserRole.contains("ROLE_MANAGER")) {
            throw new IllegalStateException("Only cashiers or managers can delete transactions");
        }

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        if (!"pending".equals(transaction.getStatus())) {
            throw new IllegalArgumentException("Only pending transactions can be deleted");
        }

        transactionRepository.delete(transaction);
    }

    public List<TransactionDTO> getPendingTransactions() {
        return transactionRepository.findByStatus("pending")
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setProductCode(transaction.getProductCode());
        dto.setProductName(transaction.getProductName());
        dto.setQuantity(transaction.getQuantity());
        dto.setProfit(transaction.getProfit());
        dto.setStatus(transaction.getStatus());
        dto.setCreatedBy(transaction.getCreatedBy());
        return dto;
    }
}
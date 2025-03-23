package com.store.inventory_management.service;

import com.store.inventory_management.dto.TransactionDTO;
import com.store.inventory_management.model.Inventory;
import com.store.inventory_management.model.Transaction;
import com.store.inventory_management.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private InventoryService inventoryService;

    public TransactionDTO createTransaction(String productCode, int quantity, String managerEmail) {
        Inventory inventory = inventoryService.getInventoryByProductCode(productCode);
        if (inventory.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient inventory quantity");
        }

        double profit = (inventory.getSellingPrice() - inventory.getCostPrice()) * quantity;
        Transaction transaction = new Transaction();
        transaction.setProductCode(productCode);
        transaction.setProductName(inventory.getProductName());
        transaction.setQuantity(quantity);
        transaction.setProfit(profit);
        transaction.setStatus("pending");
        transaction.setCreatedBy(managerEmail);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToDTO(savedTransaction);
    }

    public TransactionDTO updateTransactionStatus(String transactionId, String newStatus) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        if (!"pending".equals(transaction.getStatus())) {
            throw new IllegalArgumentException("Only pending transactions can be updated");
        }

        if (!"accepted".equals(newStatus) && !"rejected".equals(newStatus)) {
            throw new IllegalArgumentException("Status must be 'accepted' or 'rejected'");
        }

        if ("accepted".equals(newStatus)) {
            inventoryService.updateInventoryQuantity(transaction.getProductCode(), -transaction.getQuantity());
        }

        transaction.setStatus(newStatus);
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return mapToDTO(updatedTransaction);
    }

    public void deleteTransaction(String transactionId) {
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
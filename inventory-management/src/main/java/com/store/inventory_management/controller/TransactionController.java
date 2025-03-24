package com.store.inventory_management.controller;

import com.store.inventory_management.dto.TransactionDTO;
import com.store.inventory_management.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> createTransaction(
            @RequestParam String productCode,
            @RequestParam int quantity) {
        String managerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        TransactionDTO transaction = transactionService.createTransaction(productCode, quantity, managerEmail);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/accept/{transactionId}")
    public ResponseEntity<TransactionDTO> acceptTransaction(@PathVariable String transactionId) {
        TransactionDTO updatedTransaction = transactionService.acceptTransaction(transactionId);
        return ResponseEntity.ok(updatedTransaction);
    }

    @PutMapping("/reject/{transactionId}")
    public ResponseEntity<TransactionDTO> rejectTransaction(@PathVariable String transactionId) {
        TransactionDTO updatedTransaction = transactionService.rejectTransaction(transactionId);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String transactionId) {
        transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TransactionDTO>> getPendingTransactions() {
        List<TransactionDTO> pendingTransactions = transactionService.getPendingTransactions();
        return ResponseEntity.ok(pendingTransactions);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
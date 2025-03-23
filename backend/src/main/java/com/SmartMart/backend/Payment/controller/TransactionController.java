package com.SmartMart.backend.Payment.controller;

import com.SmartMart.backend.Payment.model.Transaction;
import com.SmartMart.backend.Payment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@PathVariable String userId) {
        return transactionService.getTransactionsByUser(userId);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }
}


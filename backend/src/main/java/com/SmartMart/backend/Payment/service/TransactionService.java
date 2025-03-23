package com.SmartMart.backend.Payment.service;

import com.SmartMart.backend.Payment.model.Transaction;
import com.SmartMart.backend.Payment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByUser(String userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> getTransactionsByPaymentMethod(String paymentMethod) {
        return transactionRepository.findByPaymentMethod(paymentMethod);
    }

    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}



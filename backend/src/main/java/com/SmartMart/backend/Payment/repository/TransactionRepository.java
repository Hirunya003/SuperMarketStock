package com.SmartMart.backend.Payment.repository;

import com.SmartMart.backend.Payment.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);
    List<Transaction> findByPaymentMethod(String paymentMethod);
    List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);
}


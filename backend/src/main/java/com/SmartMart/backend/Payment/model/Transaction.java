package com.SmartMart.backend.Payment.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private String userId;
    private double amount;
    private String paymentMethod; // e.g., CASH, CARD, ONLINE
    private Date transactionDate;
    private String status; // e.g., SUCCESS, FAILED
}

package com.SmartMart.backend.CustomerOrder.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private ObjectId oId;
    private String customerId;
    private List<OrderItem> items;
    private double totalAmount;
    private String status; // order status
    private String orderDate;

    public void setOrderDate(String orderDate) {
        this.orderDate=orderDate;
    }

    public List<OrderItem>getItems(){
        return items;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount=totalAmount;
    }

    public void setStatus(String status) {
        this.status=status;
    }
}

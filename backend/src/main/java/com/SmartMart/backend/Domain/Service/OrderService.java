package com.SmartMart.backend.Domain.Service;

import com.SmartMart.backend.Domain.Entity.Order;
import com.SmartMart.backend.Domain.Entity.OrderItem;
import com.SmartMart.backend.Domain.Entity.Product;
import com.SmartMart.backend.External.Repositories.OrderRepository;
import com.SmartMart.backend.External.Repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class OrderService {

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    public Order placeOrder(Order order){

        double totalAmount = 0;

        for(OrderItem item:order.getItems()){
            Optional<Product>product = productRepository.findById(item.getProductId());

            if(product.isEmpty()||product.get().getQuantity()<item.getQuentity()){
                throw new RuntimeException("Product "+item.getProductId() +"is out of stock!");
            }
            //set product price from the DB
            item.setPrice(product.get().getPrice());

            totalAmount+=item.getQuentity()*item.getPrice();


            //reduce stock quantity
            Product updatedProduct =product.get();

            updatedProduct.setQuantity(updatedProduct.getQuantity()- item.getQuentity());
            productRepository.save(updatedProduct);
        }
        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING");
        order.setOrderDate(LocalDate.now().toString());

        return orderRepository.save(order);

    }
    public List<Order> getOrdersByCustomer(String customerId){
        return orderRepository.findByCustomerId(customerId);
    }

    public Order updateOrderStatus(String oId, String status){
        Order order = orderRepository.findById(oId).orElse(null);

        if(order!=null){
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
}

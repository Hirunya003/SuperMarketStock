package com.SmartMart.backend.CustomerOrder.controller;

import com.SmartMart.backend.CustomerOrder.Model.Order;
import com.SmartMart.backend.CustomerOrder.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
        @PostMapping("/place")
        public Order placeOrder(@RequestBody Order order){
            return orderService.placeOrder(order);
        }

        @GetMapping("/{customerId}")
        public List<Order>getOrdersByCustomer(@PathVariable String customerId){
        return orderService.getOrdersByCustomer(customerId);
        }

        @PutMapping("/{oId}/status")
        public Order updateOrderStatus(@PathVariable String oId, @RequestParam String status){
        return orderService.updateOrderStatus(oId,status);
    }


}

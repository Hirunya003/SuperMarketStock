package com.SmartMart.backend.Application.Controllers;

import com.SmartMart.backend.Domain.Model.Order;
import com.SmartMart.backend.Domain.Service.OrderService;
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

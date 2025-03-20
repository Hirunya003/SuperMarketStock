package com.SmartMart.backend.Supplier.controller;

import com.SmartMart.backend.Supplier.model.PurchaseOrder;
import com.SmartMart.backend.Supplier.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService orderService;

    @GetMapping("/{id}/status")
    public PurchaseOrder getOrderStatus(@PathVariable String id) {
        return orderService.updateOrderStatus(id, null); // Just fetch for now
    }

    @PutMapping("/{id}/status")
    public PurchaseOrder updateOrderStatus(@PathVariable String id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }
}

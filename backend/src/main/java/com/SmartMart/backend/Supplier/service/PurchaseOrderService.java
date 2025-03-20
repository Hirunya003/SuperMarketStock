package com.SmartMart.backend.Supplier.service;

import com.SmartMart.backend.Supplier.model.InventoryItem;
import com.SmartMart.backend.Supplier.model.PurchaseOrder;
import com.SmartMart.backend.Supplier.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository orderRepository;
    @Autowired
    private SupplierService supplierService;

    public PurchaseOrder generatePurchaseOrder(InventoryItem item) {
        PurchaseOrder order = new PurchaseOrder();
        order.setSupplierId("some-supplier-id"); // Logic to select supplier
        order.setItemIds(List.of(item.getId()));
        order.setStatus("Pending");
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public PurchaseOrder updateOrderStatus(String orderId, String status) {
        PurchaseOrder order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        if (status.equals("Delivered")) {
            order.setDeliveryDate(LocalDateTime.now());
        }
        return orderRepository.save(order);
    }
}

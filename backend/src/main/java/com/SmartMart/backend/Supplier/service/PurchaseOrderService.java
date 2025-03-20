package com.SmartMart.backend.Supplier.service;

import com.SmartMart.backend.Supplier.model.InventoryItem;
import com.SmartMart.backend.Supplier.model.PurchaseOrder;
import com.SmartMart.backend.Supplier.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository orderRepository;
    private final SupplierService supplierService;

    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository orderRepository, SupplierService supplierService) {
        this.orderRepository = orderRepository;
        this.supplierService = supplierService;
    }

    public PurchaseOrder generatePurchaseOrder(InventoryItem item) {
        PurchaseOrder order = new PurchaseOrder();
        // Logic to select supplier
        order.setSupplierId("some-supplier-id");
        order.setItemIds(List.of(item.getId()));
        order.setStatus("Pending");
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Optional<PurchaseOrder> getOrder(String id) {
        return orderRepository.findById(id);
    }

    public Optional<PurchaseOrder> updateOrderStatus(String orderId, String status) {
        PurchaseOrder order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            if (status.equals("Delivered")) {
                order.setDeliveryDate(LocalDateTime.now());
            }
            return Optional.of(orderRepository.save(order));
        }
        return Optional.empty();
    }
}

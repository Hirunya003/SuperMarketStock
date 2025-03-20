package com.SmartMart.backend.Supplier.service;

import com.SmartMart.backend.Supplier.model.InventoryItem;
import com.SmartMart.backend.Supplier.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    public InventoryItem addItem(InventoryItem item) {
        return inventoryRepository.save(item);
    }

    public void checkLowStock() {
        List<InventoryItem> items = inventoryRepository.findAll();
        for (InventoryItem item : items) {
            if (item.getStockLevel() <= item.getLowStockThreshold()) {
                purchaseOrderService.generatePurchaseOrder(item);
            }
        }
    }
}
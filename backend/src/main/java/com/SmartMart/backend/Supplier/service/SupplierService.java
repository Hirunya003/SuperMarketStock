package com.SmartMart.backend.Supplier.service;

import com.SmartMart.backend.Supplier.model.Supplier;
import com.SmartMart.backend.Supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public void updatePerformanceScore(String supplierId, double score) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
        supplier.setPerformanceScore(score);
        supplierRepository.save(supplier);
    }
}

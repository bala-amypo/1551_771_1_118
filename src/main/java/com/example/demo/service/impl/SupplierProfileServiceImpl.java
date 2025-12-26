package com.example.demo.service.impl;

import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.SupplierProfileRepository;

public class SupplierProfileServiceImpl {

    private final SupplierProfileRepository repository;

    public SupplierProfileServiceImpl(SupplierProfileRepository repository) {
        this.repository = repository;
    }

    public SupplierProfile getBySupplierCode(String supplierCode) {
        return repository.findBySupplierCode(supplierCode)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }
}

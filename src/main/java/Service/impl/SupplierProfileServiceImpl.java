package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.SupplierProfile;
import com.example.demo.service.SupplierProfileService;

@Service
public class SupplierProfileServiceImpl implements SupplierProfileService {

    private final SupplierProfileRepository repository;

    public SupplierProfileServiceImpl(SupplierProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public SupplierProfile createSupplier(SupplierProfile supplier) {
        return repository.save(supplier);
    }

    @Override
    public SupplierProfile getSupplierById(Long id) throws SupplierNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + id));
    }

    @Override
    public SupplierProfile getBySupplierCode(String supplierCode) {
        return repository.findBySupplierCode(supplierCode)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with code: " + supplierCode));
    }

    @Override
    public List<SupplierProfile> getAllSuppliers() {
        return repository.findAll();
    }

    @Override
    public SupplierProfile updateSupplierStatus(Long id, boolean active) {
        SupplierProfile supplier = repository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + id));
        supplier.setActive(active);
        return repository.save(supplier);
    }
}

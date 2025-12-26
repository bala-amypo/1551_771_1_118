package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.SupplierProfileRepository;
import com.example.demo.service.SupplierProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierProfileServiceImpl implements SupplierProfileService {

    private final SupplierProfileRepository repository;

    public SupplierProfileServiceImpl(SupplierProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public SupplierProfile createSupplier(SupplierProfile supplier) {
        repository.findBySupplierCode(supplier.getSupplierCode())
                .ifPresent(s -> { throw new IllegalArgumentException("Duplicate supplierCode"); });
        return repository.save(supplier);
    }

    @Override
    public SupplierProfile getSupplierById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    }

    @Override
    public SupplierProfile getBySupplierCode(String code) {
        return repository.findBySupplierCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    }

    @Override
    public List<SupplierProfile> getAllSuppliers() {
        return repository.findAll();
    }

    @Override
    public SupplierProfile updateSupplierStatus(Long id, boolean active) {
        SupplierProfile supplier = getSupplierById(id);
        supplier.setActive(active);
        return repository.save(supplier);
    }
}

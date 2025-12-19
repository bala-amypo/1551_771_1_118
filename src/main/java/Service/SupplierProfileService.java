package com.example.demo.service;

import java.util.List;

import com.example.demo.model.SupplierProfile;

public interface SupplierProfileService {
    SupplierProfile createSupplier(SupplierProfile supplier);
    SupplierProfile getSupplierById(Long id) throws SupplierNotFoundException;
    SupplierProfile getBySupplierCode(String supplierCode);
    List<SupplierProfile> getAllSuppliers();
    SupplierProfile updateSupplierStatus(Long id, boolean active);
}

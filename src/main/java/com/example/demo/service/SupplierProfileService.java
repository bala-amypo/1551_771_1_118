package com.example.demo.service;

import com.example.demo.entity.SupplierProfile;

import java.util.List;
import java.util.Optional;

public interface SupplierProfileService {

    SupplierProfile createSupplier(SupplierProfile supplier);

    SupplierProfile getSupplierById(Long id);

    Optional<SupplierProfile> getSupplierBySupplierCode(String supplierCode);

    List<SupplierProfile> getAllSuppliers();

    SupplierProfile updateSupplierStatus(Long id, boolean active);
}

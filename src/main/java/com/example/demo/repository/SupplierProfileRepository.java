package com.example.demo.repository;

import com.example.demo.model.SupplierProfile;

import java.util.Optional;

public interface SupplierProfileRepository {

    Optional<SupplierProfile> findBySupplierCode(String supplierCode);

    Optional<SupplierProfile> findById(Long id);
}

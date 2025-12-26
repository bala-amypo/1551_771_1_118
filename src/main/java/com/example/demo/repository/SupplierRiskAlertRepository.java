package com.example.demo.repository;

import com.example.demo.model.SupplierRiskAlert;
import java.util.*;

public interface SupplierRiskAlertRepository {
    Optional<SupplierRiskAlert> findById(Long id);
    List<SupplierRiskAlert> findBySupplierId(Long supplierId);
    List<SupplierRiskAlert> findAll();
    SupplierRiskAlert save(SupplierRiskAlert a);
}

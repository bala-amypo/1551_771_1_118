package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.repository.SupplierRiskAlertRepository;
import java.util.*;

public class SupplierRiskAlertServiceImpl {

    private final SupplierRiskAlertRepository repo;

    public SupplierRiskAlertServiceImpl(SupplierRiskAlertRepository r) {
        this.repo = r;
    }

    public SupplierRiskAlert createAlert(SupplierRiskAlert a) {
        a.setResolved(false);
        return repo.save(a);
    }

    public List<SupplierRiskAlert> getAlertsBySupplier(Long supplierId) {
        return repo.findBySupplierId(supplierId);
    }

    public SupplierRiskAlert resolveAlert(Long id) {
        SupplierRiskAlert a = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found"));
        a.setResolved(true);
        return repo.save(a);
    }

    public List<SupplierRiskAlert> getAllAlerts() {
        return repo.findAll();
    }
}

package com.example.demo.service;

import com.example.demo.model.SupplierRiskAlert;
import java.util.List;

public interface SupplierRiskAlertService {
    SupplierRiskAlert createAlert(SupplierRiskAlert a);
    List<SupplierRiskAlert> getAlertsBySupplier(Long supplierId);
    SupplierRiskAlert resolveAlert(Long id);
    List<SupplierRiskAlert> getAllAlerts();
}

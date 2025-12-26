package com.example.demo.controller;

import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.service.SupplierRiskAlertService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk-alerts")
public class SupplierRiskAlertController {

    private final SupplierRiskAlertService alertService;

    public SupplierRiskAlertController(SupplierRiskAlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public SupplierRiskAlert create(@Valid @RequestBody SupplierRiskAlert alert) {
        return alertService.createAlert(alert);
    }

    @PutMapping("/{id}/resolve")
    public SupplierRiskAlert resolve(@PathVariable Long id) {
        return alertService.resolveAlert(id);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<SupplierRiskAlert> getBySupplier(@PathVariable Long supplierId) {
        return alertService.getAlertsBySupplier(supplierId);
    }

    @GetMapping("/{id}")
    public SupplierRiskAlert getById(@PathVariable Long id) {
        return alertService.getAllAlerts().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Alert not found"));
    }

    @GetMapping
    public List<SupplierRiskAlert> getAll() {
        return alertService.getAllAlerts();
    }
}

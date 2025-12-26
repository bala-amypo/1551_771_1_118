package com.example.demo.controller;

import com.example.demo.model.SupplierProfile;
import com.example.demo.service.SupplierProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierProfileController {

    private final SupplierProfileService supplierService;

    public SupplierProfileController(
            SupplierProfileService supplierService
    ) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<SupplierProfile>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{supplierCode}")
    public ResponseEntity<SupplierProfile> getBySupplierCode(
            @PathVariable String supplierCode
    ) {
        return supplierService
                .getSupplierBySupplierCode(supplierCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SupplierProfile> createSupplier(
            @RequestBody SupplierProfile supplier
    ) {
        return ResponseEntity.ok(
                supplierService.createSupplier(supplier)
        );
    }
}

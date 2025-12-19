package com.example.demo.controller;

import com.example.demo.entity.SupplierProfile;
import com.example.demo.service.SupplierProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierProfileController {

    private final SupplierProfileService supplierService;

    public SupplierProfileController(SupplierProfileService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public SupplierProfile createSupplier(@Valid @RequestBody SupplierProfile supplier) {
        return supplierService.createSupplier(supplier);
    }

    @PutMapping("/{id}")
    public SupplierProfile updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        return supplierService.updateSupplierStatus(id, active);
    }

    @GetMapping("/{id}")
    public SupplierProfile getById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @GetMapping
    public List<SupplierProfile> getAll() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/lookup/{code}")
    public SupplierProfile getByCode(@PathVariable String code) {
        return supplierService.getBySupplierCode(code)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }
}

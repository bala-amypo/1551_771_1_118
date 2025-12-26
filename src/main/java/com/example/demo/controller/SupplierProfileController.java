package com.example.demo.controller;

import com.example.demo.model.SupplierProfile;
import com.example.demo.service.SupplierProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierProfileController {

    private final SupplierProfileService service;

    public SupplierProfileController(SupplierProfileService service) {
        this.service = service;
    }

    @PostMapping
    public SupplierProfile create(@RequestBody SupplierProfile s) {
        return service.createSupplier(s);
    }

    @GetMapping("/{id}")
    public SupplierProfile get(@PathVariable Long id) {
        return service.getSupplierById(id);
    }

    @GetMapping
    public List<SupplierProfile> all() {
        return service.getAllSuppliers();
    }

    @PutMapping("/{id}/status")
    public SupplierProfile update(@PathVariable Long id,
                                  @RequestParam boolean active) {
        return service.updateSupplierStatus(id, active);
    }
}

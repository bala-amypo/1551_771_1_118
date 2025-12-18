package com.example.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier Profile", description = "Operations related to supplier management")
public class SupplierProfileController {

    // 1. POST / - Create supplier
    @PostMapping("/")
    @Operation(summary = "Create a new supplier")
    public ResponseEntity<String> createSupplier(@RequestBody Object supplierDto) {
        // Logic to save supplier
        return new ResponseEntity<>("Supplier created successfully", HttpStatus.CREATED);
    }

    // 2. GET /{id} - Get supplier
    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID")
    public ResponseEntity<Object> getSupplier(@PathVariable Long id) {
        // Logic to fetch supplier by ID
        return ResponseEntity.ok("Supplier details for ID: " + id);
    }

    // 3. GET / - List all
    @GetMapping("/")
    @Operation(summary = "List all suppliers")
    public ResponseEntity<List<Object>> listAll() {
        // Logic to fetch all suppliers
        return ResponseEntity.ok(Collections.emptyList());
    }

    // 4. PUT /{id}/status - Update status
    @PutMapping("/{id}/status")
    @Operation(summary = "Update supplier status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody String status) {
        // Logic to update status
        return ResponseEntity.ok("Status updated for supplier ID: " + id);
    }

    // 5. GET /lookup/{supplierCode} - Lookup
    @GetMapping("/lookup/{supplierCode}")
    @Operation(summary = "Lookup supplier by code")
    public ResponseEntity<Object> lookup(@PathVariable String supplierCode) {
        // Logic to find supplier by code
        return ResponseEntity.ok("Supplier found for code: " + supplierCode);
    }
}
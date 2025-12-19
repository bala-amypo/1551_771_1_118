package com.example.demo.controller;

import com.example.demo.entity.PurchaseOrderRecord;
import com.example.demo.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService poService;

    public PurchaseOrderController(PurchaseOrderService poService) {
        this.poService = poService;
    }

    @PostMapping
    public PurchaseOrderRecord create(@Valid @RequestBody PurchaseOrderRecord po) {
        return poService.createPurchaseOrder(po);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<PurchaseOrderRecord> getBySupplier(@PathVariable Long supplierId) {
        return poService.getPOsBySupplier(supplierId);
    }

    @GetMapping("/{id}")
    public PurchaseOrderRecord getById(@PathVariable Long id) {
        return poService.getPOById(id)
                .orElseThrow(() -> new RuntimeException("PO not found"));
    }

    @GetMapping
    public List<PurchaseOrderRecord> getAll() {
        return poService.getAllPurchaseOrders();
    }
}

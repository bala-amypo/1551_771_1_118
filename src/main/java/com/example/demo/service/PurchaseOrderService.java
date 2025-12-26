package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import java.util.*;

public class PurchaseOrderServiceImpl {

    private final PurchaseOrderRecordRepository poRepo;
    private final SupplierProfileRepository supplierRepo;

    public PurchaseOrderServiceImpl(PurchaseOrderRecordRepository p,
                                    SupplierProfileRepository s) {
        this.poRepo = p;
        this.supplierRepo = s;
    }

    public PurchaseOrderRecord createPurchaseOrder(PurchaseOrderRecord po) {
        SupplierProfile supplier = supplierRepo.findById(po.getSupplierId())
                .orElseThrow(() -> new BadRequestException("Invalid supplierId"));

        if (!supplier.getActive()) {
            throw new BadRequestException("must be active");
        }

        if (po.getQuantity() <= 0) {
            throw new BadRequestException("Quantity must be > 0");
        }

        return poRepo.save(po);
    }

    public List<PurchaseOrderRecord> getPOsBySupplier(Long supplierId) {
        return poRepo.findBySupplierId(supplierId);
    }

    public Optional<PurchaseOrderRecord> getPOById(Long id) {
        return poRepo.findById(id);
    }

    public List<PurchaseOrderRecord> getAllPurchaseOrders() {
        return poRepo.findAll();
    }
}

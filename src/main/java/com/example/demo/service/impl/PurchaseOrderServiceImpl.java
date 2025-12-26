package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierProfileRepository;
import com.example.demo.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRecordRepository poRepo;
    private final SupplierProfileRepository supplierRepo;

    public PurchaseOrderServiceImpl(PurchaseOrderRecordRepository poRepo,
                                    SupplierProfileRepository supplierRepo) {
        this.poRepo = poRepo;
        this.supplierRepo = supplierRepo;
    }

    @Override
    public PurchaseOrderRecord createPurchaseOrder(PurchaseOrderRecord po) {
        SupplierProfile supplier = supplierRepo.findById(po.getSupplierId())
                .orElseThrow(() -> new BadRequestException("Invalid supplierId"));

        if (!supplier.getActive()) {
            throw new BadRequestException("must be active");
        }

        if (po.getQuantity() <= 0) {
            throw new BadRequestException("quantity must be > 0");
        }

        return poRepo.save(po);
    }

    @Override
    public List<PurchaseOrderRecord> getPOsBySupplier(Long supplierId) {
        return poRepo.findBySupplierId(supplierId);
    }

    @Override
    public PurchaseOrderRecord getPOById(Long id) {
        return poRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("Invalid supplierId"));
    }

    @Override
    public List<PurchaseOrderRecord> getAllPurchaseOrders() {
        return poRepo.findAll();
    }
}

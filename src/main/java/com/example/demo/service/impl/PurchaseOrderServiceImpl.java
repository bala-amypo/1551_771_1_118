package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseOrderRecord;
import com.example.demo.entity.SupplierProfile;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierProfileRepository;
import com.example.demo.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRecordRepository poRepository;
    private final SupplierProfileRepository supplierRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRecordRepository poRepository,
                                    SupplierProfileRepository supplierRepository) {
        this.poRepository = poRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public PurchaseOrderRecord createPurchaseOrder(PurchaseOrderRecord po) {
        SupplierProfile supplier = supplierRepository.findById(po.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Invalid supplierId"));

        if (!supplier.getActive()) {
            throw new RuntimeException("Supplier must be active");
        }

        return poRepository.save(po);
    }

    @Override
    public List<PurchaseOrderRecord> getPOsBySupplier(Long supplierId) {
        return poRepository.findBySupplierId(supplierId);
    }

    @Override
    public Optional<PurchaseOrderRecord> getPOById(Long id) {
        return poRepository.findById(id);
    }

    @Override
    public List<PurchaseOrderRecord> getAllPurchaseOrders() {
        return poRepository.findAll();
    }
}

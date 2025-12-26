package com.example.demo.repository;

import com.example.demo.model.PurchaseOrderRecord;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderRecordRepository {

    PurchaseOrderRecord save(PurchaseOrderRecord po);

    List<PurchaseOrderRecord> findBySupplierId(Long supplierId);

    Optional<PurchaseOrderRecord> findById(Long id);

    List<PurchaseOrderRecord> findAll();
}

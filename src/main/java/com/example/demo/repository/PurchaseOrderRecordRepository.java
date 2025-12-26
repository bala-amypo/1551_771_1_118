package com.example.demo.repository;

import com.example.demo.model.PurchaseOrderRecord;
import java.util.*;

public interface PurchaseOrderRecordRepository {
    Optional<PurchaseOrderRecord> findById(Long id);
    List<PurchaseOrderRecord> findBySupplierId(Long supplierId);
    List<PurchaseOrderRecord> findAll();
    PurchaseOrderRecord save(PurchaseOrderRecord p);
}

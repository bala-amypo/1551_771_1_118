package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.DelayScoreService;
import com.example.demo.service.SupplierRiskAlertService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {

    private final DelayScoreRecordRepository scoreRepository;
    private final PurchaseOrderRecordRepository poRepository;
    private final DeliveryRecordRepository deliveryRepository;
    private final SupplierProfileRepository supplierRepository;
    private final SupplierRiskAlertService alertService;

    public DelayScoreServiceImpl(
            DelayScoreRecordRepository scoreRepository,
            PurchaseOrderRecordRepository poRepository,
            DeliveryRecordRepository deliveryRepository,
            SupplierProfileRepository supplierRepository,
            SupplierRiskAlertService alertService) {

        this.scoreRepository = scoreRepository;
        this.poRepository = poRepository;
        this.deliveryRepository = deliveryRepository;
        this.supplierRepository = supplierRepository;
        this.alertService = alertService;
    }

    @Override
    public DelayScoreRecord computeDelayScore(Long poId) {

        PurchaseOrderRecord po = poRepository.findById(poId)
                .orElseThrow(() -> new RuntimeException("PO not found"));

        SupplierProfile supplier = supplierRepository.findById(po.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        if (!supplier.getActive()) {
            throw new RuntimeException("Inactive supplier");
        }

        List<DeliveryRecord> deliveries = deliveryRepository.findByPoId(poId);
        if (deliveries.isEmpty()) {
            throw new RuntimeException("No deliveries");
        }

        DeliveryRecord latest = deliveries.get(deliveries.size() - 1);

        int delayDays = Math.max(
                0,
                latest.getActualDeliveryDate()
                        .compareTo(po.getPromisedDeliveryDate())
        );

        DelayScoreRecord record = new DelayScoreRecord();
        record.setPoId(poId);
        record.setSupplierId(po.getSupplierId());
        record.setDelayDays(delayDays);

        if (delayDays == 0) {
            record.setDelaySeverity("ON_TIME");
        } else if (delayDays <= 3) {
            record.setDelaySeverity("MINOR");
        } else {
            record.setDelaySeverity("SEVERE");
        }

        record.setScore(100.0 - delayDays * 5);

        return scoreRepository.save(record);
    }

    @Override
    public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
        return scoreRepository.findBySupplierId(supplierId);
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return scoreRepository.findAll();
    }

    @Override
    public Optional<DelayScoreRecord> getScoreById(Long id) {
        return scoreRepository.findById(id);
    }
}

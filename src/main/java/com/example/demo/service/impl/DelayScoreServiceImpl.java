package com.example.demo.service.impl;

import com.example.demo.entity.DelayScoreRecord;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.entity.PurchaseOrderRecord;
import com.example.demo.entity.SupplierProfile;
import com.example.demo.repository.*;
import com.example.demo.service.DelayScoreService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {

    private final DelayScoreRecordRepository scoreRepository;
    private final PurchaseOrderRecordRepository poRepository;
    private final DeliveryRecordRepository deliveryRepository;
    private final SupplierProfileRepository supplierRepository;

    public DelayScoreServiceImpl(
            DelayScoreRecordRepository scoreRepository,
            PurchaseOrderRecordRepository poRepository,
            DeliveryRecordRepository deliveryRepository,
            SupplierProfileRepository supplierRepository
    ) {
        this.scoreRepository = scoreRepository;
        this.poRepository = poRepository;
        this.deliveryRepository = deliveryRepository;
        this.supplierRepository = supplierRepository;
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
        long delayDays = ChronoUnit.DAYS.between(
                po.getPromisedDeliveryDate(),
                latest.getActualDeliveryDate()
        );

        String severity;
        double score;

        if (delayDays <= 0) {
            severity = "ON_TIME";
            score = 100;
        } else if (delayDays <= 3) {
            severity = "MINOR";
            score = 75;
        } else if (delayDays <= 7) {
            severity = "MODERATE";
            score = 50;
        } else {
            severity = "SEVERE";
            score = 0;
        }

        DelayScoreRecord record = new DelayScoreRecord();
        record.setPoId(poId);
        record.setSupplierId(po.getSupplierId());
        record.setDelayDays((int) delayDays);
        record.setDelaySeverity(severity);
        record.setScore(score);

        return scoreRepository.save(record);
    }

    @Override
    public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
        return scoreRepository.findBySupplierId(supplierId);
    }

    @Override
    public Optional<DelayScoreRecord> getScoreById(Long id) {
        return scoreRepository.findById(id);
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return scoreRepository.findAll();
    }
}

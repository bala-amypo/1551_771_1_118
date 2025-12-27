package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DelayScoreService;
import com.example.demo.service.SupplierRiskAlertService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {

    private final DelayScoreRecordRepository delayRepo;
    private final PurchaseOrderRecordRepository poRepo;
    private final DeliveryRecordRepository deliveryRepo;
    private final SupplierProfileRepository supplierRepo;
    private final SupplierRiskAlertService alertService;

    public DelayScoreServiceImpl(
            DelayScoreRecordRepository delayRepo,
            PurchaseOrderRecordRepository poRepo,
            DeliveryRecordRepository deliveryRepo,
            SupplierProfileRepository supplierRepo,
            SupplierRiskAlertService alertService) {

        this.delayRepo = delayRepo;
        this.poRepo = poRepo;
        this.deliveryRepo = deliveryRepo;
        this.supplierRepo = supplierRepo;
        this.alertService = alertService;
    }

    @Override
    public DelayScoreRecord computeDelayScore(Long poId) {

        PurchaseOrderRecord po = poRepo.findById(poId)
                .orElseThrow(() -> new BadRequestException("Purchase order not found"));

        SupplierProfile supplier = supplierRepo.findById(po.getSupplierId())
                .orElseThrow(() -> new BadRequestException("Invalid supplierId"));

        if (!supplier.getActive()) {
            throw new BadRequestException("Inactive supplier");
        }

        List<DeliveryRecord> deliveries = deliveryRepo.findByPoId(poId);
        if (deliveries.isEmpty()) {
            throw new BadRequestException("No deliveries");
        }

        DeliveryRecord d = deliveries.get(0);

        long delayDays = ChronoUnit.DAYS.between(
                po.getPromisedDeliveryDate(),
                d.getActualDeliveryDate());

        if (delayDays < 0) delayDays = 0;

        String severity;
        if (delayDays == 0) severity = "ON_TIME";
        else if (delayDays <= 3) severity = "MINOR";
        else if (delayDays <= 7) severity = "MODERATE";
        else severity = "SEVERE";

        double score = Math.max(0, 100 - (delayDays * 5));

        DelayScoreRecord record = new DelayScoreRecord();
        record.setPoId(poId);
        record.setSupplierId(po.getSupplierId());
        record.setDelayDays((int) delayDays);
        record.setDelaySeverity(severity);
        record.setScore(score);

        DelayScoreRecord saved = delayRepo.save(record);

        if ("SEVERE".equals(severity)) {
            SupplierRiskAlert alert = new SupplierRiskAlert();
            alert.setSupplierId(po.getSupplierId());
            alert.setAlertLevel("HIGH");
            alert.setMessage("Severe delivery delay detected");
            alertService.createAlert(alert);
        }

        return saved;
    }

    @Override
    public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
        return delayRepo.findBySupplierId(supplierId);
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return delayRepo.findAll();
    }
}
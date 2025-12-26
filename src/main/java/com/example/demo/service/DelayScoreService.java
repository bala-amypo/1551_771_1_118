package com.example.demo.service.impl;

import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import java.util.*;

public class DelayScoreServiceImpl {

    private final DelayScoreRecordRepository scoreRepo;
    private final PurchaseOrderRecordRepository poRepo;
    private final DeliveryRecordRepository deliveryRepo;
    private final SupplierProfileRepository supplierRepo;
    private final SupplierRiskAlertServiceImpl alertService;

    public DelayScoreServiceImpl(
            DelayScoreRecordRepository s,
            PurchaseOrderRecordRepository p,
            DeliveryRecordRepository d,
            SupplierProfileRepository sp,
            SupplierRiskAlertServiceImpl a) {
        this.scoreRepo = s;
        this.poRepo = p;
        this.deliveryRepo = d;
        this.supplierRepo = sp;
        this.alertService = a;
    }

    public DelayScoreRecord computeDelayScore(Long poId) {
        PurchaseOrderRecord po = poRepo.findById(poId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));

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
        int delayDays = (int) (d.getActualDeliveryDate().toEpochDay()
                - po.getPromisedDeliveryDate().toEpochDay());

        if (delayDays < 0) delayDays = 0;

        String severity =
                delayDays == 0 ? "ON_TIME" :
                delayDays <= 3 ? "MINOR" :
                delayDays <= 7 ? "MODERATE" : "SEVERE";

        double score = Math.max(0, 100 - delayDays * 5);

        DelayScoreRecord r = new DelayScoreRecord();
        r.setSupplierId(supplier.getId());
        r.setPoId(poId);
        r.setDelayDays(delayDays);
        r.setDelaySeverity(severity);
        r.setScore(score);

        scoreRepo.save(r);

        if ("SEVERE".equals(severity)) {
            SupplierRiskAlert alert = new SupplierRiskAlert();
            alert.setSupplierId(supplier.getId());
            alert.setAlertLevel("HIGH");
            alertService.createAlert(alert);
        }

        return r;
    }

    public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
        return scoreRepo.findBySupplierId(supplierId);
    }

    public List<DelayScoreRecord> getAllScores() {
        return scoreRepo.findAll();
    }
}

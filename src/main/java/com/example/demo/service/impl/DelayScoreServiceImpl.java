package com.example.demo.service.impl;

import com.example.demo.model.DelayScoreRecord;
import com.example.demo.model.DeliveryRecord;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.model.SupplierProfile;
import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.repository.DelayScoreRecordRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierRiskAlertRepository;
import com.example.demo.service.DelayScoreService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {

    private final PurchaseOrderRecordRepository purchaseOrderRepository;
    private final DeliveryRecordRepository deliveryRepository;
    private final DelayScoreRecordRepository delayScoreRepository;
    private final SupplierRiskAlertRepository alertRepository;

    public DelayScoreServiceImpl(
            PurchaseOrderRecordRepository purchaseOrderRepository,
            DeliveryRecordRepository deliveryRepository,
            DelayScoreRecordRepository delayScoreRepository,
            SupplierRiskAlertRepository alertRepository
    ) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.deliveryRepository = deliveryRepository;
        this.delayScoreRepository = delayScoreRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public DelayScoreRecord calculateDelayScore(Long poId, SupplierProfile supplier) {

        Optional<PurchaseOrderRecord> poOpt = purchaseOrderRepository.findById(poId);
        if (poOpt.isEmpty()) {
            throw new RuntimeException("Purchase order not found");
        }

        PurchaseOrderRecord po = poOpt.get();

        Optional<DeliveryRecord> deliveryOpt =
                deliveryRepository.findFirstByPoIdOrderByActualDeliveryDateDesc(poId);

        if (deliveryOpt.isEmpty()) {
            throw new RuntimeException("Delivery record not found");
        }

        DeliveryRecord delivery = deliveryOpt.get();

        long delayDays = ChronoUnit.DAYS.between(
                po.getPromisedDeliveryDate(),
                delivery.getActualDeliveryDate()
        );

        if (delayDays < 0) {
            delayDays = 0;
        }

        // ✅ FIX: calculatedScore is now properly defined
        int calculatedScore;
        if (delayDays == 0) {
            calculatedScore = 0;
        } else if (delayDays <= 3) {
            calculatedScore = 10;
        } else if (delayDays <= 7) {
            calculatedScore = 30;
        } else {
            calculatedScore = 50;
        }

        DelayScoreRecord record = new DelayScoreRecord();
        record.setPoId(poId);
        record.setDelayDays((int) delayDays);
        record.setScore(calculatedScore);
        record.setDelaySeverity(
                calculatedScore <= 10 ? "LOW" :
                calculatedScore <= 30 ? "MEDIUM" : "HIGH"
        );

        DelayScoreRecord savedRecord = delayScoreRepository.save(record);

        // Create alert for HIGH delays
        if (calculatedScore >= 30) {
            SupplierRiskAlert alert = new SupplierRiskAlert();
            alert.setSupplierId(supplier.getId());
            alert.setAlertLevel(
                    calculatedScore >= 50 ? "CRITICAL" : "WARNING"
            );
            alert.setResolved(false);

            alertRepository.save(alert);
        }

        return savedRecord;
    }
}

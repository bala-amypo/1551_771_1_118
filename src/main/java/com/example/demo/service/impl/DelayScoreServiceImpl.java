package com.example.demo.service.impl;

import com.example.demo.model.DelayScoreRecord;
import com.example.demo.model.DeliveryRecord;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.repository.DelayScoreRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.repository.PurchaseOrderRepository;
import com.example.demo.service.DelayScoreService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final DeliveryRecordRepository deliveryRepository;
    private final DelayScoreRepository delayScoreRepository;

    public DelayScoreServiceImpl(
            PurchaseOrderRepository purchaseOrderRepository,
            DeliveryRecordRepository deliveryRepository,
            DelayScoreRepository delayScoreRepository
    ) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.deliveryRepository = deliveryRepository;
        this.delayScoreRepository = delayScoreRepository;
    }

    // ===============================
    // CALCULATE SCORE
    // ===============================
    @Override
    public DelayScoreRecord calculateScore(Long poId) {

        PurchaseOrderRecord po = purchaseOrderRepository
                .findById(poId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));

        Optional<DeliveryRecord> deliveryOpt =
                deliveryRepository.findFirstByPoIdOrderByActualDeliveryDateDesc(poId);

        if (deliveryOpt.isEmpty() || po.getPromisedDeliveryDate() == null) {
            return null;
        }

        DeliveryRecord delivery = deliveryOpt.get();

        long delayDays = ChronoUnit.DAYS.between(
                po.getPromisedDeliveryDate(),
                delivery.getActualDeliveryDate()
        );

        int score = delayDays <= 0 ? 100 : Math.max(0, 100 - (int) delayDays * 5);

        DelayScoreRecord record = new DelayScoreRecord();
        record.setPoId(poId);
        record.setDelayDays((int) delayDays);
        record.setScore(score);
        record.setDelaySeverity(delayDays <= 0 ? "NONE" : "HIGH");

        return delayScoreRepository.save(record);
    }

    // ===============================
    // GET ALL SCORES  ✅ REQUIRED
    // ===============================
    @Override
    public List<DelayScoreRecord> getAllScores() {
        return delayScoreRepository.findAll();
    }
}

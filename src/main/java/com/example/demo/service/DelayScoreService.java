package com.example.demo.service.impl;

import com.example.demo.model.DelayScoreRecord;
import com.example.demo.model.DeliveryRecord;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.repository.DelayScoreRecordRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.service.DelayScoreService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {

    private final PurchaseOrderRecordRepository purchaseOrderRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final DelayScoreRecordRepository delayScoreRepository;

    public DelayScoreServiceImpl(
            PurchaseOrderRecordRepository purchaseOrderRepository,
            DeliveryRecordRepository deliveryRecordRepository,
            DelayScoreRecordRepository delayScoreRepository
    ) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
        this.delayScoreRepository = delayScoreRepository;
    }

    @Override
    public DelayScoreRecord calculateDelayScore(Long poId) {

        PurchaseOrderRecord po = purchaseOrderRepository.findById(poId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));

        DeliveryRecord delivery = deliveryRecordRepository
                .findFirstByPoIdOrderByActualDeliveryDateDesc(poId)
                .orElseThrow(() -> new RuntimeException("Delivery record not found"));

        long delayDays = ChronoUnit.DAYS.between(
                po.getPromisedDeliveryDate(),
                delivery.getActualDeliveryDate()
        );

        if (delayDays < 0) {
            delayDays = 0;
        }

        int score = (int) Math.min(delayDays * 10, 100);

        DelayScoreRecord record = new DelayScoreRecord();
        record.setPoId(poId);
        record.setDelayDays((int) delayDays);
        record.setScore(score);
        record.setDelaySeverity(
                delayDays == 0 ? "ON_TIME" :
                delayDays <= 3 ? "LOW" :
                delayDays <= 7 ? "MEDIUM" : "HIGH"
        );

        return delayScoreRepository.save(record);
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return delayScoreRepository.findAll();
    }
}

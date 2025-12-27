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

    private final PurchaseOrderRecordRepository poRepo;
    private final DeliveryRecordRepository deliveryRepo;
    private final DelayScoreRecordRepository delayRepo;

    public DelayScoreServiceImpl(
            PurchaseOrderRecordRepository poRepo,
            DeliveryRecordRepository deliveryRepo,
            DelayScoreRecordRepository delayRepo
    ) {
        this.poRepo = poRepo;
        this.deliveryRepo = deliveryRepo;
        this.delayRepo = delayRepo;
    }

    @Override
    public DelayScoreRecord calculateDelayScore(Long poId) {

        PurchaseOrderRecord po = poRepo.findById(poId)
                .orElseThrow(() -> new RuntimeException("PO not found"));

        DeliveryRecord delivery = deliveryRepo
                .findFirstByPoIdOrderByActualDeliveryDateDesc(poId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        long delayDays = ChronoUnit.DAYS.between(
                po.getPromisedDeliveryDate(),
                delivery.getActualDeliveryDate()
        );

        if (delayDays < 0) delayDays = 0;

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

        return delayRepo.save(record);
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return delayRepo.findAll();
    }
}

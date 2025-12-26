package com.example.demo.service;

import com.example.demo.entity.DeliveryRecord;
import java.util.List;

public interface DeliveryRecordService {
    DeliveryRecord recordDelivery(DeliveryRecord d);
    List<DeliveryRecord> getDeliveriesByPO(Long poId);
    List<DeliveryRecord> getAllDeliveries();
}

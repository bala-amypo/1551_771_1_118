package com.example.demo.repository;

import com.example.demo.model.DeliveryRecord;

import java.util.List;
import java.util.Optional;

public interface DeliveryRecordRepository {

    DeliveryRecord save(DeliveryRecord delivery);

    List<DeliveryRecord> findByPoId(Long poId);

    List<DeliveryRecord> findAll();

    Optional<DeliveryRecord> findById(Long id);
}

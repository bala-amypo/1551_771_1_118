package com.example.demo.repository;

import com.example.demo.model.DeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRecordRepository
        extends JpaRepository<DeliveryRecord, Long> {

    Optional<DeliveryRecord> findFirstByPoIdOrderByActualDeliveryDateDesc(Long poId);

    List<DeliveryRecord> findByPoId(Long poId);
}

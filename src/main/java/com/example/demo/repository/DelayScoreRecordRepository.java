package com.example.demo.repository;

import com.example.demo.model.DelayScoreRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DelayScoreRecordRepository extends JpaRepository<DelayScoreRecord, Long> {
    DelayScoreRecord findByPoId(Long poId);
    List<DelayScoreRecord> findBySupplierId(Long supplierId);
}

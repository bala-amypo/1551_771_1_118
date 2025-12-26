package com.example.demo.repository;

import com.example.demo.model.DelayScoreRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DelayScoreRecordRepository
        extends JpaRepository<DelayScoreRecord, Long> {

    // Used when computing score by PO
    Optional<DelayScoreRecord> findByPoId(Long poId);

    // 🔴 THIS METHOD IS REQUIRED (THIS IS YOUR ERROR)
    List<DelayScoreRecord> findBySupplierId(Long supplierId);
}

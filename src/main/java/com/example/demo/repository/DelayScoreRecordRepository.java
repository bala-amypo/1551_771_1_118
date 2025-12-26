package com.example.demo.repository;

import com.example.demo.model.DelayScoreRecord;
import java.util.List;

@Repository
public interface DelayScoreRecordRepository
        extends JpaRepository<DelayScoreRecord, Long> {
            List<DelayScoreRecord> findBySupplierId(long supplierId);
}

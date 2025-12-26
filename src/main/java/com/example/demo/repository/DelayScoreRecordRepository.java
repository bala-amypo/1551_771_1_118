package com.example.demo.repository;

import com.example.demo.model.DelayScoreRecord;
import java.util.List;

public interface DelayScoreRecordRepository {

    List<DelayScoreRecord> findBySupplierId(long supplierId);
}

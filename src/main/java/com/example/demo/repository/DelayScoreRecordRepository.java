package com.example.demo.repository;

import com.example.demo.entity.DelayScoreRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DelayScoreRecordRepository extends JpaRepository<DelayScoreRecord, Long> {

    List<DelayScoreRecord> findBySupplierCode(String supplierCode);
}

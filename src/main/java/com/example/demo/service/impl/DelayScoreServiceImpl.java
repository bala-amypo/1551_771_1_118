package com.example.demo.service.impl;

import com.example.demo.entity.DelayScoreRecord;
import com.example.demo.repository.DelayScoreRecordRepository;
import com.example.demo.service.DelayScoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {

    private final DelayScoreRecordRepository repository;

    public DelayScoreServiceImpl(DelayScoreRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public DelayScoreRecord computeDelayScore(Long poId) {
        DelayScoreRecord record = new DelayScoreRecord();
        record.setPoId(poId);
        record.setSupplierId(1L); // TEMP VALUE
        record.setDelayDays(2);
        record.setDelaySeverity("LOW");
        record.setScore(95.0);

        return repository.save(record);
    }

    @Override
    public DelayScoreRecord getScoreById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return repository.findAll();
    }
}

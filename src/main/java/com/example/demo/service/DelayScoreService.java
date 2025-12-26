package com.example.demo.service;

import com.example.demo.entity.DelayScoreRecord;

import java.util.List;

public interface DelayScoreService {

    DelayScoreRecord computeDelayScore(Long poId);

    DelayScoreRecord getScoreById(Long id);

    List<DelayScoreRecord> getAllScores();
}

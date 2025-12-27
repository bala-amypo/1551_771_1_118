package com.example.demo.service;

import com.example.demo.model.DelayScoreRecord;

import java.util.List;

public interface DelayScoreService {

    DelayScoreRecord calculateDelayScore(Long poId);

    List<DelayScoreRecord> getAllScores();
}

package com.example.demo.controller;

import com.example.demo.entity.DelayScoreRecord;
import com.example.demo.service.DelayScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delay-scores")
public class DelayScoreController {

    private final DelayScoreService delayScoreService;

    public DelayScoreController(DelayScoreService delayScoreService) {
        this.delayScoreService = delayScoreService;
    }

    // ✅ COMPUTE SCORE
    @PostMapping("/compute/{poId}")
    public DelayScoreRecord compute(@PathVariable Long poId) {
        return delayScoreService.computeDelayScore(poId);
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public DelayScoreRecord getById(@PathVariable Long id) {
        DelayScoreRecord record = delayScoreService.getScoreById(id);
        if (record == null) {
            throw new RuntimeException("DelayScore not found");
        }
        return record;
    }

    // ✅ GET ALL
    @GetMapping
    public List<DelayScoreRecord> getAll() {
        return delayScoreService.getAllScores();
    }
}

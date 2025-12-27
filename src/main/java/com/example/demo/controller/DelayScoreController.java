package com.example.demo.controller;

import com.example.demo.model.DelayScoreRecord;
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

    /**
     * Calculate delay score for a given Purchase Order ID
     */
    @PostMapping("/calculate/{poId}")
    public DelayScoreRecord calculateDelayScore(@PathVariable Long poId) {
        return delayScoreService.calculateDelayScore(poId);
    }

    /**
     * Get all delay score records
     */
    @GetMapping
    public List<DelayScoreRecord> getAllScores() {
        return delayScoreService.getAllScores();
    }
}

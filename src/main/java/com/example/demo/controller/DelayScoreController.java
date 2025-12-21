package com.example.demo.controller;

import com.example.demo.entity.DelayScoreRecord;
import com.example.demo.service.DelayScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delay-scores")
public class DelayScoreController {

    private final DelayScoreService delayScoreService;

    public DelayScoreController(DelayScoreService delayScoreService) {
        this.delayScoreService = delayScoreService;
    }

    @PostMapping("/compute/{poId}")
    public DelayScoreRecord compute(@PathVariable Long poId) {
        return delayScoreService.computeDelayScore(poId);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<DelayScoreRecord> getBySupplier(@PathVariable Long supplierId) {
        return delayScoreService.getScoresBySupplier(supplierId);
    }

    @GetMapping("/{id}")
    public DelayScoreRecord getById(@PathVariable Long id) {
        return delayScoreService.getScoreById(id)
                .orElseThrow(() -> new RuntimeException("Score not found"));
    }

    @GetMapping
    public List<DelayScoreRecord> getAll() {
        return delayScoreService.getAllScores();
    }
}

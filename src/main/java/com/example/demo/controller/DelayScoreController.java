package com.example.demo.controller;

import com.example.demo.service.DelayScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delay-scores")
public class DelayScoreController {

    private final DelayScoreService delayScoreService;

    public DelayScoreController(DelayScoreService delayScoreService) {
        this.delayScoreService = delayScoreService;
    }
}

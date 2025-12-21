package com.example.demo.controller;

import com.example.demo.entity.DeliveryRecord;
import com.example.demo.service.DeliveryRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryRecordController {

    private final DeliveryRecordService deliveryService;

    public DeliveryRecordController(DeliveryRecordService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public DeliveryRecord record(@Valid @RequestBody DeliveryRecord delivery) {
        return deliveryService.recordDelivery(delivery);
    }

    @GetMapping("/po/{poId}")
    public List<DeliveryRecord> getByPo(@PathVariable Long poId) {
        return deliveryService.getDeliveriesByPO(poId);
    }

    @GetMapping("/{id}")
    public DeliveryRecord getById(@PathVariable Long id) {
        return deliveryService.getDeliveryById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }

    @GetMapping
    public List<DeliveryRecord> getAll() {
        return deliveryService.getAllDeliveries();
    }
}

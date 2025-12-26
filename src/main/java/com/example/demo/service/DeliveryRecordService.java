package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import java.util.*;

public class DeliveryRecordServiceImpl {

    private final DeliveryRecordRepository deliveryRepo;
    private final PurchaseOrderRecordRepository poRepo;

    public DeliveryRecordServiceImpl(DeliveryRecordRepository d,
                                     PurchaseOrderRecordRepository p) {
        this.deliveryRepo = d;
        this.poRepo = p;
    }

    public DeliveryRecord recordDelivery(DeliveryRecord d) {
        poRepo.findById(d.getPoId())
                .orElseThrow(() -> new BadRequestException("Invalid PO id"));

        if (d.getDeliveredQuantity() < 0) {
            throw new BadRequestException("Delivered quantity must be >=");
        }

        return deliveryRepo.save(d);
    }

    public List<DeliveryRecord> getDeliveriesByPO(Long poId) {
        return deliveryRepo.findByPoId(poId);
    }

    public List<DeliveryRecord> getAllDeliveries() {
        return deliveryRepo.findAll();
    }
}

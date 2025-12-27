package com.example.demo.config;

import com.example.demo.repository.*;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public SupplierProfileService supplierProfileService(
            SupplierProfileRepository repository) {
        return new SupplierProfileServiceImpl(repository);
    }

    @Bean
    public PurchaseOrderService purchaseOrderService(
            PurchaseOrderRecordRepository poRepository,
            SupplierProfileRepository supplierRepository) {
        return new PurchaseOrderServiceImpl(poRepository, supplierRepository);
    }

    @Bean
    public DeliveryRecordService deliveryRecordService(
            DeliveryRecordRepository deliveryRepository,
            PurchaseOrderRecordRepository poRepository) {
        return new DeliveryRecordServiceImpl(deliveryRepository, poRepository);
    }

    @Bean
    public SupplierRiskAlertService supplierRiskAlertService(
            SupplierRiskAlertRepository repository) {
        return new SupplierRiskAlertServiceImpl(repository);
    }

    @Bean
    public DelayScoreService delayScoreService(
            DelayScoreRecordRepository delayRepo,
            PurchaseOrderRecordRepository poRepo,
            DeliveryRecordRepository deliveryRepo,
            SupplierProfileRepository supplierRepo,
            SupplierRiskAlertService alertService) {
        return new DelayScoreServiceImpl(
                delayRepo, poRepo, deliveryRepo, supplierRepo, alertService);
    }
}
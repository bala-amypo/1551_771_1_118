package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "delivery_records")
public class DeliveryRecord {

    @Id
    private Long id;
    private String supplierCode;
    private int delayDays;

    public DeliveryRecord() {}

    public DeliveryRecord(Long id, String supplierCode, int delayDays) {
        this.id = id;
        this.supplierCode = supplierCode;
        this.delayDays = delayDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public int getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(int delayDays) {
        this.delayDays = delayDays;
    }
}

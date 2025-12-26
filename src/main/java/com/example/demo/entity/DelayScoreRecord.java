package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "delay_scores")
public class DelayScoreRecord {

    @Id
    private Long id;
    private String supplierCode;
    private double score;

    public DelayScoreRecord() {}

    public DelayScoreRecord(Long id, String supplierCode, double score) {
        this.id = id;
        this.supplierCode = supplierCode;
        this.score = score;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

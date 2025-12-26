package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "delay_score_record")
public class DelayScoreRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long poId;

    @Column(nullable = false)
    private Long supplierId;

    private int delayDays;

    private String delaySeverity;

    private double score;

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public int getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(int delayDays) {
        this.delayDays = delayDays;
    }

    public String getDelaySeverity() {
        return delaySeverity;
    }

    public void setDelaySeverity(String delaySeverity) {
        this.delaySeverity = delaySeverity;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

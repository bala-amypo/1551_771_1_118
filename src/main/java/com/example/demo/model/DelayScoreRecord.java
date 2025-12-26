package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DelayScoreRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long supplierId;
    private Long poId;
    private Integer delayDays;
    private String delaySeverity;
    private Double score;
    private LocalDateTime computedAt;

    @PrePersist
    void created() {
        computedAt = LocalDateTime.now();
    }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public void setPoId(Long poId) { this.poId = poId; }
    public void setDelayDays(Integer delayDays) { this.delayDays = delayDays; }
    public void setDelaySeverity(String delaySeverity) { this.delaySeverity = delaySeverity; }
    public void setScore(Double score) { this.score = score; }
}

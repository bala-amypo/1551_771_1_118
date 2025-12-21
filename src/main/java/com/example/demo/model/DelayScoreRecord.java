package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "delay_score_records",
    uniqueConstraints = @UniqueConstraint(columnNames = "poId")
)
@Getter
@Setter
public class DelayScoreRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long supplierId;

    @Column(nullable = false)
    private Long poId;

    @Column(nullable = false)
    private Integer delayDays;

    @Column(nullable = false, length = 20)
    private String delaySeverity;

    @Column(nullable = false)
    private Double score;

    @Column(nullable = false, updatable = false)
    private LocalDateTime computedAt;

    @PrePersist
    void onCreate() {
        this.computedAt = LocalDateTime.now();
    }
}

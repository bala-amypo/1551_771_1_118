package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_risk_alerts")
@Getter
@Setter
public class SupplierRiskAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long supplierId;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String alertLevel;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String message;

    @Column(nullable = false)
    private Boolean resolved = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime alertDate;

    @PrePersist
    void onCreate() {
        this.alertDate = LocalDateTime.now();
    }
}

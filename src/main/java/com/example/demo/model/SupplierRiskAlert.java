package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SupplierRiskAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long supplierId;
    private String alertLevel;
    private String message;
    private Boolean resolved = false;
    private LocalDateTime alertDate;

    @PrePersist
    void created() {
        alertDate = LocalDateTime.now();
    }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public void setAlertLevel(String alertLevel) { this.alertLevel = alertLevel; }
    public void setMessage(String message) { this.message = message; }
    public void setResolved(Boolean resolved) { this.resolved = resolved; }
}

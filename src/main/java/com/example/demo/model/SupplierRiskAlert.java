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

    // getters & setters
}

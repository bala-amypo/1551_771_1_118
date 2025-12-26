package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DeliveryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long poId;
    private LocalDate actualDeliveryDate;
    private Integer deliveredQuantity;
    private String notes;

    public Long getPoId() { return poId; }
    public Integer getDeliveredQuantity() { return deliveredQuantity; }
    public LocalDate getActualDeliveryDate() { return actualDeliveryDate; }
}

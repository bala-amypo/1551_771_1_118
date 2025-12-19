package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "delivery_records")
@Getter
@Setter
public class DeliveryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long poId;

    @NotNull
    @Column(nullable = false)
    private LocalDate actualDeliveryDate;

    @NotNull
    @Column(nullable = false)
    private Integer deliveredQuantity;

    @Column(length = 500)
    private String notes;
}

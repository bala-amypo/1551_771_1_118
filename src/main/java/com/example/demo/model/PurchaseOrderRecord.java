package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
    name = "purchase_order_records",
    uniqueConstraints = @UniqueConstraint(columnNames = "poNumber")
)
@Getter
@Setter
public class PurchaseOrderRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String poNumber;

    @NotNull
    @Column(nullable = false)
    private Long supplierId;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String itemDescription;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    private LocalDate promisedDeliveryDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate issuedDate;
}

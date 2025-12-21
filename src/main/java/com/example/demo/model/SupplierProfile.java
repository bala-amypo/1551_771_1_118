package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "supplier_profiles",
    uniqueConstraints = @UniqueConstraint(columnNames = "supplierCode")
)
@Getter
@Setter
public class SupplierProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String supplierCode;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String supplierName;

    @Email
    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

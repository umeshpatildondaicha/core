package com.core.darkcoders.core.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "patients")
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "contact", unique = true, nullable = false)
    private String contact;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "blood_group")
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    private String keycloakId;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}


enum BloodGroup {
    A_POSITIVE,
    A_NEGATIVE,
    B_POSITIVE,
    B_NEGATIVE,
    AB_POSITIVE,
    AB_NEGATIVE,
    O_POSITIVE,
    O_NEGATIVE
} 
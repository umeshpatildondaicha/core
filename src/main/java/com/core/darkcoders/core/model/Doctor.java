package com.core.darkcoders.core.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "global_number", unique = true)
    private String globalNumber;

    @Column(name = "personal_number", unique = true)
    private String personalNumber;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "qualifications")
    private String qualifications;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "doctor_status", nullable = false)
    private DoctorStatus doctorStatus = DoctorStatus.ACTIVE;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "keycloak_id")
    private String keycloakId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 
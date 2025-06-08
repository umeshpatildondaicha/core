package com.core.darkcoders.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DOCTORS")
@Getter
@Setter
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCTOR_ID")
    private Integer doctorId;

    @Basic
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Basic
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Basic
    @Column(name = "REGISTRATION_NUMBER", nullable = false, unique = true)
    private String registrationNumber;

    @Basic
    @Column(name = "SPECIALIZATION")
    private String specialization;

    @Basic
    @Column(name = "HOSPITAL_ID")
    private Integer hospitalId;

    @Basic
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;

    @Basic
    @Column(name = "PERSONAL_NUMBER")
    private String personalNumber;

    @Basic
    @Column(name = "GLOBAL_NUMBER")
    private String globalNumber;

    @Basic
    @Column(name = "QUALIFICATIONS")
    private String qualifications;

    @Basic
    @Column(name = "PROFILE_IMAGE_URL")
    private String profileImageUrl;

    @Basic
    @Column(name = "KEYCLOAK_ID")
    private String keycloakId;

    @Basic
    @Column(name = "DOCTOR_STATUS")
    @Enumerated(EnumType.STRING)
    private DoctorStatus doctorStatus;

    @Basic
    @UpdateTimestamp
    @Column(name = "MODIFIED_TIME", insertable = true, updatable = true)
    protected Date modifiedTime;

    @Basic
    @CreationTimestamp
    @Column(name = "CREATED_TIME", insertable = true, updatable = false)
    protected Date createdTime;
} 
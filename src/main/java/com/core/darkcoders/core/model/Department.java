package com.core.darkcoders.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DEPARTMENTS")
@Getter
@Setter
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;

    @Basic
    @Column(name = "DEPARTMENT_NAME", nullable = false)
    private String departmentName;

    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    @Basic
    @Column(name = "HOSPITAL_ID")
    private Integer hospitalId;

    @Basic
    @Column(name = "HEAD_DOCTOR_ID")
    private Integer headDoctorId;

    @Basic
    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @Basic
    @UpdateTimestamp
    @Column(name = "MODIFIED_TIME", insertable = true, updatable = true)
    protected Date modifiedTime;

    @Basic
    @CreationTimestamp
    @Column(name = "CREATED_TIME", insertable = true, updatable = false)
    protected Date createdTime;
} 
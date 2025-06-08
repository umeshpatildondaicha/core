package com.core.darkcoders.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "HOSPITALS")
@Getter
@Setter
public class Hospital implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOSPITAL_ID")
    private Integer hospitalId;

    @Basic
    @Column(name = "HOSPITAL_NAME", nullable = false)
    private String hospitalName;

    @Basic
    @Column(name = "ADDRESS")
    private String address;

    @Basic
    @Column(name = "CITY")
    private String city;

    @Basic
    @Column(name = "STATE")
    private String state;

    @Basic
    @Column(name = "COUNTRY")
    private String country;

    @Basic
    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Basic
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Basic
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "WEBSITE")
    private String website;

    @Basic
    @Column(name = "LICENSE_NUMBER")
    private String licenseNumber;

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
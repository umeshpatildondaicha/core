package com.darkcoders.platform.configuration.baseconfig.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USERS")
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Basic
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Basic
    @Column(name = "PASSWORD")
    private String password;

    @Basic
    @Column(name = "KEYCLOAK_ID")
    private String keycloakId;

    @Basic
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Basic
    @Column(name = "ENABLED")
    private Boolean enabled;

    @Basic
    @UpdateTimestamp
    @Column(name = "MODIFIED_TIME", insertable = true, updatable = true)
    protected Date modifiedTime;

    @Basic
    @CreationTimestamp
    @Column(name = "CREATED_TIME", insertable = true, updatable = false)
    protected Date createdTime;
} 
package com.darkcoders.platform.configuration.multilingual.model;

import com.core.darkcoders.core.model.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "multilingual_configuration")
@Audited
public class MultiLingualConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lingual_key", nullable = false)
    private String lingualKey;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "language_type", nullable = false)
    private String languageType;

    @Column(name = "category")
    private String category;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "app_name", nullable = false)
    private String appName;

    @PrePersist
    protected void onCreate() {
        createdTime = new Date();
        modifiedTime = new Date();
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedTime = new Date();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID_FK", updatable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    protected AppUser creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAST_MODIFIER_ID_FK")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    protected AppUser lastModifier;

    public String getkeyAndAppName() {
        return this.lingualKey.concat("_").concat(this.appName);
    }
} 
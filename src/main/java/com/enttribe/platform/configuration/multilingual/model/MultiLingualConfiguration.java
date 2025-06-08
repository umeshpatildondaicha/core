package com.enttribe.platform.configuration.multilingual.model;

import com.core.darkcoders.core.model.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "MULTILINGUAL_CONFIGURATION")
@Getter
@Setter
@EqualsAndHashCode
@Audited
public class MultiLingualConfiguration implements Serializable {

    private static final long serialVersionUID = -5093658300917781556L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "LINGUAL_KEY", nullable = false, length = 400)
    private String lingualKey;

    @Basic
    @Column(name = "DEFAULT_VALUE", columnDefinition = "text")
    private String defaultValue;

    @Basic
    @Column(name = "VALUE", nullable = false, length = 1500)
    private String value;

    @Basic
    @Column(name = "LANGUAGE_TYPE", length = 10)
    private String languageType;

    @Basic
    @Column(name = "CATEGORY", length = 200)
    private String category;

    @Basic
    @Column(name = "APP_NAME", length = 50)
    private String appName;

    @Basic
    @Column(name = "IS_DELETED", nullable = false)
    private Boolean isDeleted = false;

    @Basic
    @UpdateTimestamp
    @Column(name = "MODIFIED_TIME", insertable = true, updatable = true)
    protected Date modifiedTime;

    @Basic
    @CreationTimestamp
    @Column(name = "CREATED_TIME", insertable = true, updatable = false)
    protected Date createdTime;

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
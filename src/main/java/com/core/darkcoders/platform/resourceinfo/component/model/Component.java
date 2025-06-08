package com.core.darkcoders.platform.resourceinfo.component.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "COMPONENT")
@Data
@Getter
@Setter
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_COMPONENT_ID_FK")
    private Component parentComponent;

    @Column(name = "DETAILS", length = 500)
    private String details;

    @Column(name = "MODIFICATION_TIME", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationTime;

    @Column(name = "CREATION_TIME", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "ICON", columnDefinition = "longtext")
    private String icon;

    @Column(name = "DISPLAY_NAME", length = 255)
    private String displayName;

    @Column(name = "CREATOR_ID_FK")
    private Integer creatorId;

    @Column(name = "MODIFIER_ID_FK")
    private Integer modifierId;

    @Column(name = "ICON_NAME", columnDefinition = "text")
    private String iconName;

    @Column(name = "SETTINGS", columnDefinition = "longtext")
    private String settings;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "LAST_MODIFIER", length = 100)
    private String lastModifier;

    @Column(name = "DELETED")
    private Boolean deleted = false;

    @Column(name = "CREATOR", length = 100)
    private String creator;

    @Column(name = "APPLICATION_NAME", length = 50)
    private String applicationName;

    @Column(name = "CATEGORY", length = 20)
    private String category;

    @Column(name = "LAST_UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedTime;

    @Column(name = "APPLICATION_ID_FK")
    private Integer applicationId;

    @Column(name = "IS_APPLICATION", nullable = false)
    private Boolean isApplication = false;

    @Column(name = "USER_GROUP_ID_FK")
    private Integer userGroupId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 20)
    private ComponentType type = ComponentType.BOTH;
} 
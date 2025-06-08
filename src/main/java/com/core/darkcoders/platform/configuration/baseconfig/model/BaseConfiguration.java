package com.core.darkcoders.platform.configuration.baseconfig.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity class for base configuration.
 */
@Data
@Entity
@Table(name = "BASE_CONFIGURATION")
@FilterDef(name = "customerFilter", parameters = {
    @ParamDef(name = "customerId", type = Integer.class)
})
@Filter(name = "customerFilter", condition = "CUSTOMER_ID = :customerId")
@NamedQuery(
    name = "getBaseConfigByTag",
    query = "SELECT b FROM BaseConfiguration b " +
            "WHERE (b.configKey = :configKey AND b.configTag = :configTag AND b.applicationName = :appName) " +
            "AND (b.customerId = :customerId " +
            "OR (b.customerId = 1 AND NOT EXISTS " +
            "(SELECT 1 FROM BaseConfiguration sub WHERE sub.customerId = :customerId " +
            "AND sub.configKey = :configKey AND sub.configTag = :configTag AND sub.applicationName = :appName)))"
)
public class BaseConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CONFIG_KEY")
    private String configKey;

    @Column(name = "CONFIG_VALUE")
    private String configValue;

    @Column(name = "CONFIG_TAG")
    private String configTag;

    @Column(name = "APPLICATION_NAME")
    private String applicationName;

    @Column(name = "CUSTOMER_ID")
    private Integer customerId;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "LAST_MODIFIER")
    private String lastModifier;

    @Column(name = "MODIFICATION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationTime;

    @Column(name = "CONFIG_TYPE")
    private String configType;

    @Column(name = "INTERFACE_CONFIG")
    private Boolean interfaceConfig;

    @Column(name = "moduleid_fk")
    private Integer moduleId;

    @Column(name = "COMPONENT_ID_FK")
    private Integer componentId;

    @PrePersist
    protected void onCreate() {
        creationTime = new Date();
        modificationTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modificationTime = new Date();
    }
} 
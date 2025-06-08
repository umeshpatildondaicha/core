package com.core.darkcoders.platform.resourceinfo.component.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ComponentDataWrapper {
    private Integer id;
    private String name;
    private Integer parentComponentId;
    private String details;
    private Date modificationTime;
    private Date creationTime;
    private String icon;
    private String displayName;
    private Integer creatorId;
    private Integer modifierId;
    private String iconName;
    private String settings;
    private Boolean enabled;
    private String lastModifier;
    private Boolean deleted;
    private String creator;
    private String applicationName;
    private String category;
    private Date lastUpdatedTime;
    private Integer applicationId;
    private Boolean isApplication;
    private Integer userGroupId;
    private String type;
} 
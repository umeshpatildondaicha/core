package com.core.darkcoders.platform.resourceinfo.component.wrapper;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ComponentDataWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer componentId;
    private String name;
    private String displayName;
    private String details;
    private String image;
    private String iconName;
    private String settings;
    private boolean enabled;
    private String appName;
    private String componentType;
    private List<ComponentDataWrapper> child;

    public ComponentDataWrapper() {
    }

    public ComponentDataWrapper(Integer componentId, String name, String image, String settings, String displayName, String iconName, boolean enabled) {
        this.componentId = componentId;
        this.name = name;
        this.image = image;
        this.settings = settings;
        this.displayName = displayName;
        this.iconName = iconName;
        this.enabled = enabled;
    }
} 
package com.core.darkcoders.platform.resourceinfo.component.dto;

import com.core.darkcoders.platform.resourceinfo.component.model.Component;
import com.core.darkcoders.platform.resourceinfo.component.model.ComponentType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class ComponentResponseDTO {
    private String name;
    private String settings;
    private String displayName;
    private String moduleImage;
    private List<ComponentResponseDTO> child;
    private String icon;
    private String details;
    private String uploadfile;
    private Integer parentId;
    private String iconName;
    private Boolean enabled;
    private String hierarchy;
    private String createdBy;
    private String modifierby;
    private Long creationTime;
    private String appName;
    private String category;
    private Date lastUpdatedTime;
    private Boolean isApp;
    private Integer workGroupId;
    private String lastModifierName;
    private Date modificationTime;
    private String creatorName;
    private Integer permissionCount;
    private Integer permissionGroupCount;
    private String componentType;
    private Integer componentId;

    public static ComponentResponseDTO fromEntity(Component component) {
        if (component == null) {
            return null;
        }
        
        ComponentResponseDTO dto = new ComponentResponseDTO();
        dto.setName(component.getName());
        dto.setSettings(component.getSettings());
        dto.setDisplayName(component.getDisplayName());
        dto.setModuleImage(null);
        dto.setChild(new ArrayList<>());
        dto.setIcon(component.getIcon());
        dto.setDetails(component.getDetails());
        dto.setUploadfile(null);
        dto.setParentId(component.getParentComponent() != null ? component.getParentComponent().getId() : null);
        dto.setIconName(component.getIconName());
        dto.setEnabled(component.getEnabled());
        dto.setHierarchy(null);
        dto.setCreatedBy(component.getCreator());
        dto.setModifierby(component.getLastModifier());
        dto.setCreationTime(component.getCreationTime() != null ? component.getCreationTime().getTime() : null);
        dto.setAppName(component.getApplicationName());
        dto.setCategory(component.getCategory());
        dto.setLastUpdatedTime(component.getLastUpdatedTime());
        dto.setIsApp(component.getIsApplication());
        dto.setWorkGroupId(component.getUserGroupId());
        dto.setLastModifierName(component.getLastModifier());
        dto.setModificationTime(component.getModificationTime());
        dto.setCreatorName(component.getCreator());
        dto.setPermissionCount(0);
        dto.setPermissionGroupCount(0);
        dto.setComponentType(component.getType() != null ? component.getType().name() : null);
        dto.setComponentId(component.getId());
        return dto;
    }
} 
package com.drishti.accessmanagement.dto.permission;

import com.drishti.accessmanagement.dto.AuditableDto;
import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;

public class PermissionDto extends AuditableDto {

  private String name;
  private String description;
  
  private boolean active;
  
  private ActionDto actionDto;
  private ResourceDto resourceDto;
  
  public PermissionDto(String name) {
    this.name = name;
  }

  public PermissionDto(Long id, String name, String description, boolean active) {
    super.setId(id);
    this.name = name;
    this.description = description;
    this.active = active;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public ActionDto getActionDto() {
    return actionDto;
  }

  public void setActionDto(ActionDto actionDto) {
    this.actionDto = actionDto;
  }

  public ResourceDto getResourceDto() {
    return resourceDto;
  }

  public void setResourceDto(ResourceDto resourceDto) {
    this.resourceDto = resourceDto;
  }

  @Override
  public String toString() {
    return "PermissionDto [name=" + name + ", description=" + description + ", active=" + active + ", actionDto="
        + actionDto + ", resourceDto=" + resourceDto + "]";
  }  
}

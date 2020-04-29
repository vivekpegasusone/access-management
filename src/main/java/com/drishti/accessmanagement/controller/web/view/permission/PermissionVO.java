package com.drishti.accessmanagement.controller.web.view.permission;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.drishti.accessmanagement.controller.web.view.action.ActionVO;
import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.controller.web.view.resource.ResourceVO;

public class PermissionVO {

  private Long id;
  
  @NotNull(message = "Permission must have an Application.")
  private ApplicationVO applicationVO;

  @NotBlank(message = "Permission name can not be empty.")
  @Size(max = 50, message = "Permission name must be less then or equal to 50 characters.")
  private String name;

  @Size(max = 100, message = "Permission description must be less then or equal to 100 characters.")
  private String description;

  private boolean active = true;
  private String createdBy;
  private String updatedBy;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;
  
  private ActionVO actionVO;
  private ResourceVO resourceVO;

  public PermissionVO() {   
  }
  
  public PermissionVO(Long id) {
    this.id = id;
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
  }
  
  public ApplicationVO getApplicationVO() {
    return applicationVO;
  }

  public void setApplicationVO(ApplicationVO applicationVO) {
    this.applicationVO = applicationVO;
  }

  public ActionVO getActionVO() {
    return actionVO;
  }

  public void setActionVO(ActionVO actionVO) {
    this.actionVO = actionVO;
  }

  public ResourceVO getResourceVO() {
    return resourceVO;
  }

  public void setResourceVO(ResourceVO resourceVO) {
    this.resourceVO = resourceVO;
  }

  @Override
  public String toString() {
    return "PermissionVO [id=" + id + ", name=" + name + ", description=" + description + ", active=" + active
        + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdOn=" + createdOn + ", updatedOn="
        + updatedOn + ", actionVO=" + actionVO + ", resourceVO=" + resourceVO + "]";
  }
}

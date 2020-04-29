package com.drishti.accessmanagement.controller.web.view.role;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.controller.web.view.permission.PermissionVO;

public class RoleVO {

  private Long id;

  @NotNull(message = "Role must have an Application.")
  private ApplicationVO applicationVO;

  private PermissionVO permissionVO;
  
  @NotBlank(message = "Role name can not be empty.")
  @Size(max = 50, message = "Role name must be less then or equal to 50 characters.")
  private String name;

  @Size(max = 100, message = "Role description must be less then or equal to 100 characters.")
  private String description;

  private boolean active = true;
  
  private String createdBy;
  private String updatedBy;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

  public RoleVO() {
  }
  
  public RoleVO(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ApplicationVO getApplicationVO() {
    return applicationVO;
  }

  public void setApplicationVO(ApplicationVO applicationVO) {
    this.applicationVO = applicationVO;
  }
  
  public PermissionVO getPermissionVO() {
    return permissionVO;
  }

  public void setPermissionVO(PermissionVO permissionVO) {
    this.permissionVO = permissionVO;
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

  @Override
  public String toString() {
    return "RoleVO [id=" + id + ", applicationVO=" + applicationVO + ", permissionVO=" + permissionVO + ", name=" + name
        + ", description=" + description + ", active=" + active + ", createdBy=" + createdBy + ", updatedBy="
        + updatedBy + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
  }
}

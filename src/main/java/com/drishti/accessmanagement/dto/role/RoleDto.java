package com.drishti.accessmanagement.dto.role;

import java.util.ArrayList;
import java.util.List;

import com.drishti.accessmanagement.dto.AuditableDto;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.permission.PermissionDto;
import com.drishti.accessmanagement.dto.user.UserDto;

public class RoleDto extends AuditableDto{

  private String name;
  private String description;

  private boolean active;
  
  private PermissionDto permissionDto;
  private ApplicationDto applicationDto;

  private List<UserDto> userDtos = new ArrayList<>();

  public RoleDto(String name) {
    this.name = name;
  }

  public RoleDto(Long id, String name, String description, boolean active) {
    super.setId(id);
    this.name = name;
    this.description = description;
    this.active = active;
  }

  public Long getId() {
    return super.getId();
  }

  public void setId(Long id) {
    super.setId(id);
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

  public ApplicationDto getApplicationDto() {
    return applicationDto;
  }

  public void setApplicationDto(ApplicationDto applicationDto) {
    this.applicationDto = applicationDto;
  }
  
  public List<UserDto> getUserDtos() {
    return userDtos;
  }

  public void setUserDtos(List<UserDto> userDtos) {
    this.userDtos = userDtos;
  }

  public void addUserDto(UserDto userDto) {
    this.userDtos.add(userDto);
  }

  public PermissionDto getPermissionDto() {
    return permissionDto;
  }

  public void setPermissionDto(PermissionDto permissionDto) {
    this.permissionDto = permissionDto;
  }

  @Override
  public String toString() {
    return "RoleDto [name=" + name + ", description=" + description + ", active=" + active + ", permissionDto="
        + permissionDto + ", applicationDto=" + applicationDto + ", userDtos=" + userDtos + "]";
  }
}

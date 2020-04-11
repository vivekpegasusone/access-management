package com.drishti.accessmanagement.dto.action;

import com.drishti.accessmanagement.dto.AuditableDto;
import com.drishti.accessmanagement.dto.application.ApplicationDto;

public class ActionDto extends AuditableDto {
  
  private String name;
  private String description;

  private boolean active;
  
  private ApplicationDto applicationDto;
  
  public ActionDto(Long id) {
    super.setId(id);
  }
  
  public ActionDto(String name) {
    this.name = name;
  }

  public ActionDto(Long id, String name, String description, boolean active) {
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

  @Override
  public String toString() {
    return "ActionDto [name=" + name + ", description=" + description + ", active=" + active + ", applicationDto="
        + applicationDto + "]";
  }
}

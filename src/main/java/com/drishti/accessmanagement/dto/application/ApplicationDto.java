package com.drishti.accessmanagement.dto.application;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.drishti.accessmanagement.dto.AuditableDto;

public class ApplicationDto extends AuditableDto {

  @NotBlank(message = "Application name can not be empty.")
  @Size(max = 50, message = "Application name must be less then or equal to 50 characters.")
  private String name;

  @Size(max = 100, message = "Application description must be less then or equal to 100 characters.")
  private String description;

  private boolean active;
  
  private String createdBy;

  private String updatedBy;
  
  private LocalDateTime createdOn;
  
  private LocalDateTime updatedOn;
  
  public ApplicationDto() {
    super();
  }

  public ApplicationDto(Long id,
      @NotBlank(message = "Application name can not be empty.") @Size(max = 50, message = "Application name must be less then or equal to 50 characters.") String name,
      @Size(max = 100, message = "Application description must be less then or equal to 100 characters.") String description,
      boolean active, String createdBy, String updatedBy, LocalDateTime createdOn, LocalDateTime updatedOn) {
    super.setId(id);
    this.name = name;
    this.description = description;
    this.active = active;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdOn = createdOn;
    this.updatedOn = updatedOn;
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
    return "ApplicationDto [id=" + getId() + ", name=" + name + ", description=" + description + ", active=" + active + "]";
  } 
}

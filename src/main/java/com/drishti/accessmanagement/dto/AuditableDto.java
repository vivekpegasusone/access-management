package com.drishti.accessmanagement.dto;

import java.time.LocalDateTime;

public class AuditableDto {

  private Long id;
  private String createdBy;
  private String updatedBy;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
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
}

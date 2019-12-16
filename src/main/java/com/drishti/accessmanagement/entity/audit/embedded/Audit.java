package com.drishti.accessmanagement.entity.audit.embedded;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class Audit implements Serializable {

  private static final long serialVersionUID = -6204251799233984847L;

  @CreatedDate
  @Column(name = "createdOn", nullable = false, updatable = false)
  private LocalDateTime createdOn;

  @CreatedBy
  @Column(name = "createdBy", nullable = false, updatable = false)
  private String createdBy;

  @LastModifiedDate
  @Column(name = "updatedOn", nullable = false)
  private LocalDateTime updatedOn;

  @LastModifiedBy
  @Column(name = "updatedBy", nullable = false)
  private String updatedBy;

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  @Override
  public String toString() {
    return "Audit{" +
        "createdOn=" + createdOn +
        ", createdBy='" + createdBy + '\'' +
        ", updatedOn=" + updatedOn +
        ", updatedBy='" + updatedBy + '\'' +
        '}';
  }
}

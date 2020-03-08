package com.drishti.accessmanagement.repository.entity.audit;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable{

  private static final long serialVersionUID = 1123723101780382515L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  
  @CreatedDate
  @Column(name = "createdOn", nullable = false, updatable = false, insertable = true)
  private LocalDateTime createdOn;

  @CreatedBy
  @Column(name = "createdBy", nullable = false, updatable = false, insertable = true)
  private String createdBy;

  @LastModifiedDate
  @Column(name = "updatedOn", nullable = false)
  private LocalDateTime updatedOn;

  @LastModifiedBy
  @Column(name = "updatedBy", nullable = false)
  private String updatedBy;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
    return "Auditable [createdOn=" + createdOn + ", createdBy=" + createdBy + ", updatedOn=" + updatedOn
        + ", updatedBy=" + updatedBy + "]";
  }  
  
}


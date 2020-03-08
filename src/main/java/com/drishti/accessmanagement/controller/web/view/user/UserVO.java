package com.drishti.accessmanagement.controller.web.view.user;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.controller.web.view.role.RoleVO;

public class UserVO {

  private Long id;
  
  @NotNull(message = "User must be assigned an Application.")
  private ApplicationVO applicationVO;
  
  @NotNull(message = "User must be assigned a Role.")
  private RoleVO roleVO;
  
  @NotBlank(message = "User login id can not be empty.")
  @Size(max = 25, message = "User login id must be less then or equal to 25 characters.")
  private String loginId;

  @Size(max = 25, message = "User first name must be less then or equal to 25 characters.")
  private String firstName;

  @NotBlank(message = "User last name can not be empty.")
  @Size(max = 25, message = "User last name must be less then or equal to 25 characters.")
  private String lastName;

  @Email(message = "User should have a valid email address.")
  @Size(max = 50, message = "User email id must be less then or equal to 50 characters.")
  private String emailId;

  @Size(max = 25, message = "User password must be less then or equal to 25 characters.")
  private String password;

  private boolean active = true;
  
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

  public ApplicationVO getApplicationVO() {
    return applicationVO;
  }

  public void setApplicationVO(ApplicationVO applicationVO) {
    this.applicationVO = applicationVO;
  }

  public RoleVO getRoleVO() {
    return roleVO;
  }

  public void setRoleVO(RoleVO roleVO) {
    this.roleVO = roleVO;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
    return "UserVO [id=" + id + ", applicationVO=" + applicationVO + ", roleVO=" + roleVO + ", loginId=" + loginId
        + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId + ", password=" + password
        + ", active=" + active + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdOn=" + createdOn
        + ", updatedOn=" + updatedOn + "]";
  }

}

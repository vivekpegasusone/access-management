package com.drishti.accessmanagement.dto.user;

import com.drishti.accessmanagement.dto.AuditableDto;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.role.RoleDto;

public class UserDto extends AuditableDto{

  private String loginId;
  private String firstName;
  private String lastName;
  private String emailId;
  private String password;
  private boolean active = true;
  
  private RoleDto roleDto;
  private ApplicationDto applicationDto;

  public UserDto() {
  }
  
  public UserDto(Long id) {
    super.setId(id);
  }

  public UserDto(String loginId) {
    this.loginId = loginId;
  }

  public UserDto(Long id, String loginId, String password, String firstName, String lastName, String emailId, boolean active) {
    super.setId(id);
    this.loginId = loginId;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
    this.active = active;
  }

  public Long getId() {
    return super.getId();
  }

  public void setId(Long id) {
    super.setId(id);
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

  public RoleDto getRoleDto() {
    return roleDto;
  }

  public void setRoleDto(RoleDto roleDto) {
    this.roleDto = roleDto;
  }
  
  public ApplicationDto getApplicationDto() {
    return applicationDto;
  }

  public void setApplicationDto(ApplicationDto applicationDto) {
    this.applicationDto = applicationDto;
  }

  @Override
  public String toString() {
    return "UserDto{" +
        "id=" + getId() +
        ", loginId='" + loginId + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", emailId='" + emailId + '\'' +
        ", active=" + active +
        '}';
  }
}

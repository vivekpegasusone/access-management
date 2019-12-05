package com.drishti.accessmanagement.dto.user;

import com.drishti.accessmanagement.dto.role.RoleView;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserView {

  private Long id;

  @NotBlank(message = "User login id can not be empty.")
  @Size(max = 25, message = "User login id must be less then or equal to 25 characters.")
  private String loginId;

  @Size(max = 25, message = "User first name must be less then or equal to 25 characters.")
  private String firstName;

  @Size(max = 25, message = "User last name must be less then or equal to 25 characters.")
  private String lastName;

  @Email
  @Size(max = 50, message = "User email id must be less then or equal to 50 characters.")
  private String emailId;

  @NotNull(message = "User password can not be null.")
  @Size(max = 25, message = "User password must be less then or equal to 25 characters.")
  private String password;

  private boolean active;

  private List<RoleView> roles = new ArrayList<>();

  public UserView(@NotBlank(message = "User login id can not be empty.")
                  @Size(max = 25, message = "User login id must be less then or equal to 25 characters.") String loginId) {
    this.loginId = loginId;
  }

  public UserView(Long id,
                  @NotBlank(message = "User login id can not be empty.") @Size(max = 25, message = "User login id must be less then or equal to 25 characters.") String loginId,
                  @Size(max = 25, message = "User first name must be less then or equal to 25 characters.") String firstName,
                  @Size(max = 25, message = "User last name must be less then or equal to 25 characters.") String lastName,
                  @Email @Size(max = 50, message = "User email id must be less then or equal to 50 characters.") String emailId,
                  boolean active) {
    this.id = id;
    this.loginId = loginId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
    this.active = active;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public List<RoleView> getRoles() {
    return roles;
  }

  public void setRoles(List<RoleView> roles) {
    this.roles = roles;
  }

  public void addRole(RoleView role) {
    this.roles.add(role);
  }

  @Override
  public String toString() {
    return "UserView{" +
        "id=" + id +
        ", loginId='" + loginId + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", emailId='" + emailId + '\'' +
        ", active=" + active +
        '}';
  }
}

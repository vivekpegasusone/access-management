package com.drishti.accessmanagement.repository.entity.user;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.audit.Auditable;
import com.drishti.accessmanagement.repository.entity.role.Role;

@Entity
@Table(name = "users")
public class User extends Auditable {

  private static final long serialVersionUID = -1978668993823338927L;

  @Column(name = "loginId", unique = true)
  private String loginId;

  @Column(name = "firstName")
  private String firstName;

  @Column(name = "lastName")
  private String lastName;

  @Column(name = "emailId")
  private String emailId;

  @Column(name = "password")
  private String password;

  @Column(name = "active")
  private boolean active;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "applicationId", nullable = false)
  private Application application;

  @ManyToOne(fetch = FetchType.LAZY)
  private Role role;

  protected User() {
  }

  private User(UserBuilder builder) {
    this.setId(builder.id);
    this.setLoginId(builder.loginId);
    this.setActive(builder.active);
    this.setFirstName(builder.firstName);
    this.setLastName(builder.lastName);
    this.setEmailId(builder.emailId);
    this.setPassword(builder.password);
    this.setRole(builder.role);
    this.setApplication(builder.application);
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

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    User user = (User) o;
    return Objects.equals(getId(), user.getId()) && loginId.equals(user.loginId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), loginId);
  }

  @Override
  public String toString() {
    return "User [id=" + getId() + ", loginId=" + loginId + ", firstName=" + firstName + ", lastName=" + lastName
        + ", emailId=" + emailId + ", active=" + active + "]";
  }

  public static class UserBuilder {
    private Long id;
    private boolean active;

    private String loginId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;

    private Role role;    
    private Application application;

    public UserBuilder(String loginId) {
      this.loginId = loginId;
    }

    public UserBuilder setId(Long id) {
      this.id = id;
      return this;
    }

    public UserBuilder setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public UserBuilder setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public UserBuilder setEmailId(String emailId) {
      this.emailId = emailId;
      return this;
    }

    public UserBuilder setPassword(String password) {
      this.password = password;
      return this;
    }

    public UserBuilder setActive(boolean active) {
      this.active = active;
      return this;
    }

    public UserBuilder setRole(Role role) {
      this.role = role;
      return this;
    }

    public UserBuilder setApplication(Application application) {
      this.application = application;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}

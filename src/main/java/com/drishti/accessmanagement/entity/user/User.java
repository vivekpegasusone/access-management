package com.drishti.accessmanagement.entity.user;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;

import com.drishti.accessmanagement.entity.role.Role;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

  private static final long serialVersionUID = -1978668993823338927L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Size(max = 25)
  @Column(name = "loginId", unique = true)
  private String loginId;

  @Size(max = 25)
  @Column(name = "firstName")
  private String firstName;

  @Size(max = 25)
  @Column(name = "lastName")
  private String lastName;

  @Email
  @Size(max = 50)
  @Column(name = "emailId")
  private String emailId;

  @NotNull
  @Size(max = 25)
  @Column(name = "password")
  private String password;

  @Column(name = "enabled")
  private boolean enabled;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "userId"),
      inverseJoinColumns = @JoinColumn(name = "roleId")
  )
  private List<Role> roles = new ArrayList<>();

  @Embedded
  private Audit audit = new Audit();

  public User() {
  }

  public User(@NotNull @Size(max = 25) String loginId,
              @Size(max = 25) String firstName,
              @Size(max = 25) String lastName,
              @Email @Size(max = 50) String emailId,
              @NotNull @Size(max = 25) String password,
              boolean enabled) {
    this.loginId = loginId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
    this.password = password;
    this.enabled = enabled;
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

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Audit getAudit() {
    return audit;
  }

  public void setAudit(Audit audit) {
    this.audit = audit;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public void addRole(Role role) {
    roles.add(role);
    role.getUsers().add(this);
  }

  public void removeRole(Role role) {
    roles.remove(role);
    role.getUsers().remove(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id) &&
        loginId.equals(user.loginId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, loginId);
  }

  @Override
  public String  toString() {
    return "User{" +
        "id=" + id +
        ", loginId='" + loginId + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", emailId='" + emailId + '\'' +
        ", enabled=" + enabled +
        ", audit=" + audit +
        '}';
  }
}

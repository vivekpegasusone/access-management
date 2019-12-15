package com.drishti.accessmanagement.entity.user;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;
import com.drishti.accessmanagement.entity.role.Role;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "userId"),
      inverseJoinColumns = @JoinColumn(name = "roleId")
  )
  private List<Role> roles = new ArrayList<>();

  @Embedded
  private Audit audit = new Audit();

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
    this.setRoles(builder.roles);
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
        ", active=" + active +
        ", audit=" + audit +
        '}';
  }

  public static class UserBuilder {
    private Long id;
    private boolean active;

    private String loginId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;

    private List<Role> roles = new ArrayList<>();

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

    public UserBuilder setRoles(List<Role> roles) {
      this.roles = roles;
      return this;
    }

    public UserBuilder addRole(Role role) {
      this.roles.add(role);
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}

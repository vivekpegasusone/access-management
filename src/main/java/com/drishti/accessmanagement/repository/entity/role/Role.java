package com.drishti.accessmanagement.repository.entity.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.audit.Auditable;
import com.drishti.accessmanagement.repository.entity.permission.Permission;
import com.drishti.accessmanagement.repository.entity.user.User;

@Entity
@Table(
    name = "roles",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"applicationId", "permissionId"},
        name = "UK_Application_Permission")
    }
)
public class Role extends Auditable {

  private static final long serialVersionUID = -9041515827455699269L;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "active")
  private boolean active;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "applicationId", nullable = false)
  private Application application;

  @OneToOne
  @JoinColumn(name = "permissionId", nullable = true)
  private Permission permission;
  
  @OneToMany(mappedBy = "role", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<User> users = new ArrayList<>();

  protected Role() {
  }

  private Role(RoleBuilder builder) {
    this.setId(builder.id);
    this.name = builder.name;
    this.description = builder.description;
    this.active = builder.active;
    this.users = builder.users;
    this.permission = builder.permission;
    this.application= builder.application;    
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

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
    permission.setRole(this);
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Role role = (Role) o;
    return Objects.equals(getId(), role.getId()) && name.equals(role.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), name);
  }

  @Override
  public String toString() {
    return "Role [id=" + getId() + ", name=" + name + ", description=" + description + ", active=" + active + "]";
  }

  public static class RoleBuilder {
    private Long id;
    private boolean active;
    private String name;
    private String description;
    private Permission permission;
    private Application application;
    
    private List<User> users = new ArrayList<>();

    public RoleBuilder(String name) {
      this.name = name;
    }

    public RoleBuilder setId(Long id) {
      this.id = id;
      return this;
    }

    public RoleBuilder setActive(boolean active) {
      this.active = active;
      return this;
    }

    public RoleBuilder setDescription(String description) {
      this.description = description;
      return this;
    }
    
    public RoleBuilder setPermission(Permission permission) {
      this.permission = permission;
      return this;
    }
    
    public RoleBuilder setApplication(Application application) {
      this.application = application;
      return this;
    }

    public RoleBuilder setUsers(List<User> users) {
      this.users = users;
      return this;
    }

    public RoleBuilder addUser(User user) {
      this.users.add(user);
      return this;
    }

    public Role build() {
      return new Role(this);
    }
  }
}

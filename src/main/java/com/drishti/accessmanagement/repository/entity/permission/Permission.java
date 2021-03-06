package com.drishti.accessmanagement.repository.entity.permission;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.audit.Auditable;
import com.drishti.accessmanagement.repository.entity.resource.Resource;
import com.drishti.accessmanagement.repository.entity.role.Role;

@Entity
@Table(
    name = "permissions",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"resourceId", "actionId"},
        name = "UK_Resource_Action")
    }
)
public class Permission extends Auditable {

  private static final long serialVersionUID = 992320152375313938L;

  @Column(name = "name", unique = true)
  private String name;
  
  @Column(name = "description")
  private String description;
  
  @Column(name = "active")
  private boolean active;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "actionId", nullable = false)
  private Action action;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "resourceId", nullable = false)
  private Resource resource;
  
  @OneToOne(mappedBy = "permission")
  private Role role;

  protected Permission() {
  }

  private Permission(PermissionBuilder builder) {
    this.setId(builder.id);
    this.setName(builder.name);
    this.setDescription(builder.description);
    this.setActive(builder.active);
    this.setAction(builder.action);
    this.setResource(builder.resource);
    this.setRole(builder.role);
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Permission that = (Permission) o;
    return Objects.equals(getId(), that.getId()) &&
        name.equals(that.name) &&
        action.equals(that.action) &&
        resource.equals(that.resource);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), name, action, resource);
  }

  @Override
  public String toString() {
    return "Permission [id=" + getId() + ", name=" + name + ", action=" + action + ", resource=" + resource + "]";
  }

  public static class PermissionBuilder {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    
    private Role role;
    private Action action;
    private Resource resource;    

    public PermissionBuilder(String name) {
      this.name = name;
    }

    public PermissionBuilder setId(Long id) {
      this.id = id;
      return this;
    }

    public PermissionBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public PermissionBuilder setRole(Role role) {
      this.role = role;
      return this;
    }
    
    public PermissionBuilder setAction(Action action) {
      this.action = action;
      return this;
    }

    public PermissionBuilder setResource(Resource resource) {
      this.resource = resource;
      return this;
    }

    public PermissionBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public PermissionBuilder setActive(boolean active) {
      this.active = active;
      return this;
    }

    public Permission build() {
      return new Permission(this);
    }
  }
}

package com.drishti.accessmanagement.repository.entity.permission;

import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.audit.Auditable;
import com.drishti.accessmanagement.repository.entity.resource.Resource;

import javax.persistence.*;
import java.util.Objects;

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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "actionId", nullable = false)
  private Action action;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "resourceId", nullable = false)
  private Resource resource;

  protected Permission() {
  }

  private Permission(PermissionBuilder builder) {
    this.setId(builder.id);
    this.setName(builder.name);
    this.setAction(builder.action);
    this.setResource(builder.resource);
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

    public PermissionBuilder setAction(Action action) {
      this.action = action;
      return this;
    }

    public PermissionBuilder setResource(Resource resource) {
      this.resource = resource;
      return this;
    }

    public Permission build() {
      return new Permission(this);
    }
  }
}

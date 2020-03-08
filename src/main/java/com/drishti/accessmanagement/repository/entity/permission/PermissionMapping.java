package com.drishti.accessmanagement.repository.entity.permission;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.drishti.accessmanagement.repository.entity.audit.Auditable;
import com.drishti.accessmanagement.repository.entity.role.Role;

@Entity
@Table(name = "permission_mappings")
public class PermissionMapping extends Auditable {

  private static final long serialVersionUID = -7084677788576384413L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "roleId", nullable = false)
  private Role role;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "permissionId", nullable = false)
  private Permission permission;

  protected PermissionMapping() {
  }

  private PermissionMapping(PermissionMappingBuilder builder) {
    this.setId(builder.id);
    this.setRole(builder.role);
    this.setPermission(builder.permission);
  }

  public Long getId() {
    return super.getId();
  }

  public void setId(Long id) {
    super.setId(id);
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    PermissionMapping that = (PermissionMapping) o;
    return getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public String toString() {
    return "PermissionMapping [id=" + getId() + ", role=" + role + ", permission=" + permission + "]";
  }

  public static class PermissionMappingBuilder {
    private Long id;
    private Role role;
    private Permission permission;

    public PermissionMappingBuilder setId(Long id) {
      this.id = id;
      return this;
    }

    public PermissionMappingBuilder setRole(Role role) {
      this.role = role;
      return this;
    }

    public PermissionMappingBuilder setPermission(Permission permission) {
      this.permission = permission;
      return this;
    }

    public PermissionMapping build() {
      return new PermissionMapping(this);
    }
  }
}

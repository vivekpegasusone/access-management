package com.drishti.accessmanagement.entity.permission;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;
import com.drishti.accessmanagement.entity.role.Role;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "permission_mappings")
@EntityListeners(AuditingEntityListener.class)
public class PermissionMapping implements Serializable {

  private static final long serialVersionUID = -7084677788576384413L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "roleId", nullable = false)
  private Role role;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "permissionId", nullable = false)
  private Permission permission;

  @Embedded
  private Audit audit = new Audit();

  protected PermissionMapping() {
  }

  private PermissionMapping(PermissionMappingBuilder builder) {
    this.setId(builder.id);
    this.setRole(builder.role);
    this.setPermission(builder.permission);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Audit getAudit() {
    return audit;
  }

  public void setAudit(Audit audit) {
    this.audit = audit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PermissionMapping that = (PermissionMapping) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "PermissionMapping{" +
        "id=" + id +
        ", role=" + role +
        ", permission=" + permission +
        ", audit=" + audit +
        '}';
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

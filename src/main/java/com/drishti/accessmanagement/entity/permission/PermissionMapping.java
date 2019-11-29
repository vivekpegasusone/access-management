package com.drishti.accessmanagement.entity.permission;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;
import com.drishti.accessmanagement.entity.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "permission_mappings")
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
  private Audit audit;

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
}

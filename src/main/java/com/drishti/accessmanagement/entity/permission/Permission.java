package com.drishti.accessmanagement.entity.permission;

import com.drishti.accessmanagement.entity.action.Action;
import com.drishti.accessmanagement.entity.audit.embedded.Audit;
import com.drishti.accessmanagement.entity.resource.Resource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
    name = "permissions",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"resourceId", "actionId"},
        name = "UK_Resource_Action")
    }
)
public class Permission  implements Serializable {

  private static final long serialVersionUID = 992320152375313938L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Size(max = 50)
  @Column(name = "name", unique = true)
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "actionId", nullable = false)
  private Action action;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "resourceId", nullable = false)
  private Resource resource;

  @Embedded
  private Audit audit;

  public Permission() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    Permission that = (Permission) o;
    return Objects.equals(id, that.id) &&
        name.equals(that.name) &&
        action.equals(that.action) &&
        resource.equals(that.resource);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, action, resource);
  }

  @Override
  public String toString() {
    return "Permission{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", action=" + action +
        ", resource=" + resource +
        ", audit=" + audit +
        '}';
  }
}

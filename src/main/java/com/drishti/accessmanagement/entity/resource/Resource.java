package com.drishti.accessmanagement.entity.resource;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "resources")
public class Resource implements Serializable {

  private static final long serialVersionUID = 4982374360510911739L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Size(max = 50)
  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "enabled")
  private boolean enabled;

  @Embedded
  private Audit audit;

  public Resource() {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Resource resource = (Resource) o;
    return Objects.equals(id, resource.id) &&
        name.equals(resource.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "Resource{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", enabled=" + enabled +
        ", audit=" + audit +
        '}';
  }
}

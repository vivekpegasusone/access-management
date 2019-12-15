package com.drishti.accessmanagement.entity.resource;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "resources")
@EntityListeners(AuditingEntityListener.class)
public class Resource implements Serializable {

  private static final long serialVersionUID = 4982374360510911739L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "active")
  private boolean active;

  @Embedded
  private Audit audit = new Audit();

  protected Resource() {
  }

  private Resource(ResourceBuilder builder) {
    this.setId(builder.id);
    this.setName(builder.name);
    this.setActive(builder.active);
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
        ", active=" + active +
        ", audit=" + audit +
        '}';
  }

  public static class ResourceBuilder {
    private Long id;
    private String name;
    private boolean active;

    public ResourceBuilder(String name) {
      this.name = name;
    }

    public ResourceBuilder setId(Long id) {
      this.id = id;
      return this;
    }

    public ResourceBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public ResourceBuilder setActive(boolean active) {
      this.active = active;
      return this;
    }

    public Resource build() {
      return new Resource(this);
    }
  }
}

package com.drishti.accessmanagement.repository.entity.resource;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.audit.Auditable;

@Entity
@Table(name = "resources")
public class Resource extends Auditable {

  private static final long serialVersionUID = 4982374360510911739L;

  @Column(name = "name", unique = true)
  private String name;
  
  @Column(name = "description")
  private String description;

  @Column(name = "active")
  private boolean active;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "applicationId", nullable = false)
  private Application application;

  protected Resource() {
  }

  private Resource(ResourceBuilder builder) {
    this.setId(builder.id);
    this.setName(builder.name);
    this.setDescription(builder.description);
    this.setActive(builder.active);
    this.setApplication(builder.application);
  }

  public Long getId() {
    return super.getId();
  }

  public void setId(Long id) {
    super.setId(id);;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Resource resource = (Resource) o;
    return Objects.equals(getId(), resource.getId()) &&
        name.equals(resource.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), name);
  }

  @Override
  public String toString() {
    return "Resource [id=" + getId() + ", name=" + name + ", active=" + active + ", application=" + application + "]";
  }

  public static class ResourceBuilder {
    private Long id;
    private String name;
    private String description;
    private boolean active;

    private Application application;
    
    public ResourceBuilder(String name) {
      this.name = name;
    }

    public ResourceBuilder setId(Long id) {
      this.id = id;
      return this;
    }
    
    public ResourceBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public ResourceBuilder setActive(boolean active) {
      this.active = active;
      return this;
    }

    public ResourceBuilder setApplication(Application application) {
      this.application = application;
      return this;
    }

    public Resource build() {
      return new Resource(this);
    }
  }
}

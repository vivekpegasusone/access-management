package com.drishti.accessmanagement.repository.entity.application;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.drishti.accessmanagement.repository.entity.audit.Auditable;

@Entity
@Table(name = "applications")
public class Application extends Auditable {
  
  private static final long serialVersionUID = 1757587359068119674L;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "active")
  private boolean active;

  public Application() {    
  }

  public Application(ApplicationBuilder builder) {
    this.setId(builder.id);
    this.setName(builder.name);
    this.setDescription(builder.description);
    this.setActive(builder.active);
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Application other = (Application) obj;
    return Objects.equals(getId(), other.getId()) && Objects.equals(name, other.name);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getId(), name);
  }

 

  @Override
  public String toString() {
    return "Application [id=" + getId() + ", name=" + name + ", description=" + description + ", active=" + active + "]";
  }



  public static class ApplicationBuilder {
    private Long id;
    private String name;
    private String description;
    private boolean active;

    public ApplicationBuilder(String name) {
      this.name = name;
    }

    public ApplicationBuilder setId(Long id) {
      this.id = id;
      return this;
    }

    public ApplicationBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public ApplicationBuilder setActive(boolean active) {
      this.active = active;
      return this;
    }

    public Application build() {
      return new Application(this);
    }
  }
}

package com.drishti.accessmanagement.entity.application;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;

@Entity
@Table(name = "applications")
@EntityListeners(AuditingEntityListener.class)
public class Application implements Serializable{
  
  private static final long serialVersionUID = 1757587359068119674L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "active")
  private boolean active;

  @Embedded
  private Audit audit = new Audit();

  public Application() {    
  }

  public Application(ApplicationBuilder builder) {
    this.setId(builder.id);
    this.setName(builder.name);
    this.setDescription(builder.description);
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

  public Audit getAudit() {
    return audit;
  }

  public void setAudit(Audit audit) {
    this.audit = audit;
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
    return Objects.equals(id, other.id) && Objects.equals(name, other.name);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "Application [id=" + id + ", name=" + name + ", active=" + active + ", audit=" + audit + "]";
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

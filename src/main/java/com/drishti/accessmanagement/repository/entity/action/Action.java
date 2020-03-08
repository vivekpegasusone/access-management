package com.drishti.accessmanagement.repository.entity.action;

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
@Table(name = "actions")
public class Action extends Auditable {

  private static final long serialVersionUID = 7886253356834901810L;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "active")
  private boolean active;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "applicationId", nullable = false)
  private Application application;

  protected Action() {
  }

  private Action(ActionBuilder builder) {
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
    Action action = (Action) o;
    return Objects.equals(getId(), action.getId()) &&
        name.equals(action.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), name);
  }

  @Override
  public String toString() {
    return "Action [id=" + getId() + ", name=" + name + ", description=" + description + ", active=" + active + "]";
  }

  public static class ActionBuilder {
    private Long id;
    private String name;
    private String description;
    private boolean active;

    private Application application;
    
    public ActionBuilder(String name) {
      this.name = name;
    }

    public ActionBuilder setId(Long id) {
      this.id = id;
      return this;
    }

    public ActionBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public ActionBuilder setActive(boolean active) {
      this.active = active;
      return this;
    }

    public ActionBuilder setApplication(Application application) {
      this.application = application;
      return this;
    }

    public Action build() {
      return new Action(this);
    }
  }
}

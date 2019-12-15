package com.drishti.accessmanagement.entity.action;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "actions")
@EntityListeners(AuditingEntityListener.class)
public class Action implements Serializable {

  private static final long serialVersionUID = 7886253356834901810L;

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

  protected Action() {
  }

  private Action(ActionBuilder builder) {
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Action action = (Action) o;
    return Objects.equals(id, action.id) &&
        name.equals(action.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "Action{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", active=" + active +
        ", audit=" + audit +
        '}';
  }

  public static class ActionBuilder {
    private Long id;
    private String name;
    private String description;
    private boolean active;

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

    public Action build() {
      return new Action(this);
    }
  }
}

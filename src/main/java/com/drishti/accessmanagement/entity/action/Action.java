package com.drishti.accessmanagement.entity.action;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

  @NotNull
  @Size(max = 50)
  @Column(name = "name", unique = true)
  private String name;

  @Size(max = 100)
  @Column(name = "description")
  private String description;

  @Column(name = "enabled")
  private boolean enabled;

  @Embedded
  private Audit audit;

  public Action() {
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
        ", enabled=" + enabled +
        ", audit=" + audit +
        '}';
  }
}

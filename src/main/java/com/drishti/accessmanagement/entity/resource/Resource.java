package com.drishti.accessmanagement.entity.resource;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "resources")
public class Resource implements Serializable {

  private static final long serialVersionUID = 4982374360510911739L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", length = 50, unique = true)
  private String name;

  @Column(name = "enabled")
  private boolean enabled;

  @Column(name = "createdDate", updatable = false)
  private LocalDate createdDate;

  @Column(name = "updatedDate")
  private LocalDate updatedDate;

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

  public LocalDate getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDate createdDate) {
    this.createdDate = createdDate;
  }

  public LocalDate getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(LocalDate updatedDate) {
    this.updatedDate = updatedDate;
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
        ", createdDate=" + createdDate +
        ", updatedDate=" + updatedDate +
        '}';
  }
}

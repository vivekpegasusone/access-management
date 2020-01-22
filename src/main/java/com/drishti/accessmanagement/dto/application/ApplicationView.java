package com.drishti.accessmanagement.dto.application;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ApplicationView {

  private Long id;

  @NotBlank(message = "Application name can not be empty.")
  @Size(max = 50, message = "Application name must be less then or equal to 50 characters.")
  private String name;

  @Size(max = 100, message = "Application description must be less then or equal to 100 characters.")
  private String description;

  private boolean active;

  public ApplicationView(Long id,
      @NotBlank(message = "Application name can not be empty.") @Size(max = 50, message = "Application name must be less then or equal to 50 characters.") String name,
      @Size(max = 100, message = "Application description must be less then or equal to 100 characters.") String description,
      boolean active) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.active = active;
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

  @Override
  public String toString() {
    return "ApplicationView [id=" + id + ", name=" + name + ", description=" + description + ", active=" + active + "]";
  } 
}

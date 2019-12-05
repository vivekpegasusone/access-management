package com.drishti.accessmanagement.dto.role;

import com.drishti.accessmanagement.dto.user.UserView;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class RoleView {

  private Long id;

  @NotBlank(message = "Role name can not be empty.")
  @Size(max = 50, message = "Role name must be less then or equal to 50 characters.")
  private String name;

  @Size(max = 100, message = "Role description must be less then or equal to 100 characters.")
  private String description;

  private boolean active;

  private List<UserView> users = new ArrayList<>();

  public RoleView(@NotBlank(message = "Role name can not be empty.")
                  @Size(max = 50, message = "Role name must be less then or equal to 50 characters.") String name) {
    this.name = name;
  }

  public RoleView(Long id,
                  @NotBlank(message = "Role name can not be empty.") @Size(max = 50, message = "Role name must be less then or equal to 50 characters.") String name,
                  @Size(max = 100, message = "Role description must be less then or equal to 100 characters.") String description,
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

  public List<UserView> getUsers() {
    return users;
  }

  public void setUsers(List<UserView> users) {
    this.users = users;
  }

  public void addUser(UserView userView) {
    this.users.add(userView);
  }

  @Override
  public String toString() {
    return "RoleView{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", active=" + active +
        '}';
  }
}

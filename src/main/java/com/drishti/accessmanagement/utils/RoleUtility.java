package com.drishti.accessmanagement.utils;

import com.drishti.accessmanagement.dto.role.RoleView;
import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.entity.role.Role;
import com.drishti.accessmanagement.entity.user.User;

import java.util.ArrayList;
import java.util.List;

public class RoleUtility {

  private RoleUtility() {
  }

  public static List<RoleView> prepareRoleViewsFromRoles(final List<Role> roles) {
    List<RoleView> roleViews = new ArrayList<>(roles.size());

    roles.forEach(r -> {
      RoleView roleView = prepareRoleViewFromRole(r);
      roleViews.add(roleView);
    });

    return roleViews;
  }

  public static Role prepareRoleFromRoleView(RoleView rv) {
    Role role = new Role.RoleBuilder(rv.getName())
        .setId(rv.getId())
        .setDescription(rv.getDescription())
        .setActive(rv.isActive()).build();

    List<UserView> userViews = rv.getUserViews();
    List<User> users = new ArrayList<>(userViews.size());

    userViews.forEach(u ->
        users.add(new User.UserBuilder(u.getLoginId()).setId(u.getId()).setFirstName(u.getFirstName())
            .setLastName(u.getLastName()).setEmailId(u.getEmailId()).setActive(u.isActive()).build())
    );

    role.setUsers(users);

    return role;
  }

  public static RoleView prepareRoleViewFromRole(Role role) {
    RoleView roleView = new RoleView(role.getId(), role.getName(), role.getDescription(), role.isActive());

    List<User> users = role.getUsers();
    List<UserView> userViews = new ArrayList<>(users.size());

    users.forEach(user -> {
      UserView userView = new UserView( user.getId(), user.getLoginId(), user.getFirstName(), user.getLastName(),
          user.getEmailId(), user.isActive());
      userViews.add(userView);
    });

    roleView.setUserViews(userViews);

    return roleView;
  }
}

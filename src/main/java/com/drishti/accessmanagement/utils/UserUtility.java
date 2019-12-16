package com.drishti.accessmanagement.utils;

import com.drishti.accessmanagement.dto.role.RoleView;
import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.entity.role.Role;
import com.drishti.accessmanagement.entity.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserUtility {

  private UserUtility() {
  }

  public static List<UserView> prepareUserViewsFromUsers(final List<User> users) {
    List<UserView> userViews = new ArrayList<>(users.size());

    users.forEach(u -> {
      UserView userView = prepareUserViewFromUser(u);
      userViews.add(userView);
    });

    return userViews;
  }

  public static UserView prepareUserViewFromUser(final User user) {
    UserView userView = new UserView(user.getId(), user.getLoginId(), user.getFirstName(), user.getLastName(),
        user.getEmailId(), user.isActive());

    user.getRoles().forEach(
        r -> userView.addRoleView(new RoleView(r.getId(), r.getName(), r.getDescription(), r.isActive()))
    );

    return userView;
  }

  public static User prepareUserFromUserView(final UserView uv) {
    User user = new User.UserBuilder(uv.getLoginId())
        .setId(uv.getId())
        .setFirstName(uv.getFirstName())
        .setLastName(uv.getLastName())
        .setEmailId(uv.getEmailId())
        .setActive(uv.isActive()).build();

    uv.getRoleViews().forEach(r ->
      user.addRole(new Role.RoleBuilder(r.getName()).setId(r.getId()).setDescription(r.getDescription())
          .setActive(r.isActive()).build())
    );

    return user;
  }
}

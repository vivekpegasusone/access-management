package com.drishti.accessmanagement.utility;

import com.drishti.accessmanagement.entity.role.Role;
import com.drishti.accessmanagement.entity.user.User;

import java.util.Random;

public class UserFixture {

  private static Random random = new Random();

  public static User anyUser() {
    int counter = random.nextInt(1000);
    User user = new User.UserBuilder("user" + counter)
        .setId(Long.valueOf(counter))
        .setFirstName("User")
        .setLastName(" " + counter)
        .setPassword("password" + counter)
        .setEmailId("user" + counter + "@abc.com")
        .setActive(true).build();

    counter = counter + 1;
    user.addRole(new Role.RoleBuilder("role" + counter)
        .setId(Long.valueOf(counter))
        .setDescription("This is test role " + counter + ". ")
        .setActive(true).build());

    counter = counter + 1;
    user.addRole(new Role.RoleBuilder("role" + counter)
        .setId(Long.valueOf(counter))
        .setDescription("This is test role " + counter + ". ")
        .setActive(true).build());

    return user;
  }
}

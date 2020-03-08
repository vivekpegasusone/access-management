package com.drishti.accessmanagement.utility;

import java.util.Random;

import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.role.Role;
import com.drishti.accessmanagement.repository.entity.user.User;

public class UserFixture {

  private static Random random = new Random();

  public static User anyUser() {
    int counter = random.nextInt(1000);
    return new User.UserBuilder("user" + counter)
        .setId(Long.valueOf(counter))
        .setFirstName("User")
        .setLastName(" " + counter)
        .setPassword("password" + counter)
        .setEmailId("user" + counter + "@abc.com")
        .setActive(true)
        .setRole(new Role.RoleBuilder("role" + counter)
        .setId(Long.valueOf(counter))
        .setDescription("This is test role.")
        .setActive(true).build())
        .setApplication(new Application.ApplicationBuilder("app" + counter)
        .setId(Long.valueOf(counter))
        .setDescription("This is test application.")
        .setActive(true).build()).build();
  }
}

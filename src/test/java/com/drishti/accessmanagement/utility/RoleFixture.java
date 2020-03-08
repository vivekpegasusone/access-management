package com.drishti.accessmanagement.utility;

import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.role.Role;
import com.drishti.accessmanagement.repository.entity.user.User;

import java.util.Random;

public class RoleFixture {

  private static Random random = new Random();

  public static Role anyRole() {
    int counter = random.nextInt(1000);

    return new Role.RoleBuilder("TestRole" + counter)
        .setId(Long.valueOf(counter))
        .setDescription("Rest Description " + counter)
        .setActive(true)
        .setApplication(new Application.ApplicationBuilder("app" + counter)
        .setId(Long.valueOf(counter))
        .setDescription("This is test application.")
        .setActive(true).build())
        .addUser(new User.UserBuilder("user" + counter)
            .setId(Long.valueOf(counter))
            .setFirstName("User")
            .setLastName(" " + counter)
            .setPassword("password" + counter)
            .setEmailId("user" + counter + "@abc.com")
            .setActive(true).build())
        .addUser(new User.UserBuilder("user" + (counter + 1))
            .setId(Long.valueOf(counter + 1))
            .setFirstName("User")
            .setLastName(" " + (counter + 1))
            .setPassword("password" + (counter + 1))
            .setEmailId("user" + (counter + 1) + "@abc.com")
            .setActive(true).build())
        .build();
  }
}

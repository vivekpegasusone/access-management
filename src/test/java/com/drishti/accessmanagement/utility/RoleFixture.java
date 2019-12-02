package com.drishti.accessmanagement.utility;

import com.drishti.accessmanagement.entity.role.Role;

import java.util.Random;

public class RoleFixture {

  private static Random random = new Random();

  public static Role anyRole() {
    Role role = new Role();
    role.setId(random.nextLong());
    role.setName("xvxczc");
    role.setDescription("sfiusudhkj");
    role.setActive(true);

    return role;
  }

//  public static Role anyRole() {
//    Role role = new Role();
//    role.setId(anyLong());
//    role.setName(anyString());
//    role.setDescription(anyString());
//    role.setActive(eq(true));
//
//    return role;
//  }
}

package com.drishti.accessmanagement.utility;

import com.drishti.accessmanagement.entity.role.Role;
import com.drishti.accessmanagement.entity.user.User;

import java.util.Random;

import static com.drishti.accessmanagement.utility.RoleFixture.anyRole;

public class UserFixture {

  private static Random random = new Random();

  public static User anyUser() {
    User user = new User();
    user.setId(random.nextLong());
    user.setLoginId("sgfuashf");
    user.setFirstName("ieuyfsncs");
    user.setLastName("sdjfsiajdfnh");
    user.setPassword("mkjugfgvb");
    user.setEmailId("pokjfkn@abc.com");
    user.setActive(true);

    Role role1 = anyRole();
    Role role2 = anyRole();

    user.addRole(role1);
    user.addRole(role2);

    return user;
  }

//  public static User anyUser() {
//    User user = new User();
//    user.setId(anyLong());
//    user.setLoginId(anyString());
//    user.setFirstName(anyString());
//    user.setLastName(anyString());
//    user.setPassword(anyString());
//    user.setEmailId(anyString() + eq("@abc.com"));
//    user.setActive(eq(true));
//
//    Role role1 = anyRole();
//    Role role2 = anyRole();
//
//    user.addRole(role1);
//    user.addRole(role2);
//
//    return user;
//  }
}

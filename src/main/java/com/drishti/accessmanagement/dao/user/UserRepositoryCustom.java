package com.drishti.accessmanagement.dao.user;

import com.drishti.accessmanagement.entity.user.User;

import java.util.List;

interface UserRepositoryCustom {

  List<User> getFirstNamesLike(String firstName);

  List<User> getAllActiveUsers();

  List<User> getAllInActiveUsers();

}

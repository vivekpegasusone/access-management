package com.drishti.accessmanagement.transformer;

import java.util.List;

import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.entity.user.User;

public interface Transformer {

  List<UserView> prepareUserViewsFromUsers(final List<User> users);
  
  UserView prepareUserViewFromUser(final User user);
}

package com.drishti.accessmanagement.service.user;

import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

import java.util.List;

public interface UserService {
  
  List<UserView> findAll();

  List<UserView> findActiveUsers();
  
  List<UserView> findInActiveUsers();  

  UserView findUserById(Long id) throws RecordNotFoundException;

  UserView findUserByLoginId(String loginId) throws RecordNotFoundException;

  UserView createUser(UserView userView);

  UserView updateUser(UserView userView) throws RecordNotFoundException;

  void deleteUserById(Long id) throws RecordNotFoundException;

}

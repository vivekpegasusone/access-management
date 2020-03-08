package com.drishti.accessmanagement.service.user;

import com.drishti.accessmanagement.dto.user.UserDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

import java.util.List;

public interface UserService {
  
  List<UserDto> findAll();

  List<UserDto> findActiveUsers();
  
  List<UserDto> findInActiveUsers();  

  UserDto findUserById(Long id) throws RecordNotFoundException;

  UserDto findUserByLoginId(String loginId) throws RecordNotFoundException;

  UserDto createUser(UserDto userDto);

  UserDto updateUser(UserDto userrDto) throws RecordNotFoundException;

  void deleteUserById(Long id) throws RecordNotFoundException;

}

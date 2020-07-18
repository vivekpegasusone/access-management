package com.drishti.accessmanagement.service.user;

import java.util.List;

import com.drishti.accessmanagement.dto.user.UserDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

public interface UserService {
  
  List<UserDto> findAll();

  List<UserDto> findActiveUsers();
  
  List<UserDto> findInActiveUsers();  

  UserDto findUserById(Long id) throws RecordNotFoundException;

  UserDto findUserByLoginId(String loginId) throws RecordNotFoundException;

  UserDto createUser(UserDto userDto) throws DuplicateRecordException;

  UserDto updateUser(UserDto userrDto) throws RecordNotFoundException, DuplicateRecordException;

  void deleteUserById(Long id) throws RecordNotFoundException;

}

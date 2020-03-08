package com.drishti.accessmanagement.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.drishti.accessmanagement.dto.user.UserDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.user.UserRepository;
import com.drishti.accessmanagement.repository.entity.user.User;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.user.UserTransformer;

@Service
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  
  private Transformer<User, UserDto> userTransformer = new UserTransformer();

  @Override
  public List<UserDto> findAll() {
    List<User> userList = userRepository.findAll();

    if (!userList.isEmpty()) {
      return userTransformer.transform(userList);
    } else {
      return new ArrayList<>();
    }
  }
  
  @Override
  public List<UserDto> findActiveUsers() {
    List<User> userList = userRepository.findByActiveTrue();

    if (!userList.isEmpty()) {
      return userTransformer.transform(userList);
    } else {
      return new ArrayList<>();
    }
  }
  
  @Override
  public List<UserDto> findInActiveUsers() {
    List<User> userList = userRepository.findByActiveFalse();

    if (!userList.isEmpty()) {
      return userTransformer.transform(userList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public UserDto findUserById(final Long id) {
    Optional<User> userOptional = userRepository.findById(id);

    if (userOptional.isPresent()) {
      return userTransformer.transform(userOptional.get());
    } else {
      throw new RecordNotFoundException("No record exist for given user id " + id);
    }
  }

  @Override
  public UserDto findUserByLoginId(final String loginId) {
    Optional<User> userOptional = userRepository.findByLoginId(loginId);

    if (userOptional.isPresent()) {
      return userTransformer.transform(userOptional.get());
    } else {
      throw new RecordNotFoundException("No record exist for given user loginId " + loginId);
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public UserDto createUser(final UserDto userDto) {
    return saveOrUpdateUser(userDto);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public UserDto updateUser(final UserDto userDto) {
    return saveOrUpdateUser(userDto);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteUserById(Long id) {
    User user = userRepository.findById(id).get();
    user.setActive(false);
    userRepository.save(user); 
  }

  private UserDto saveOrUpdateUser(final UserDto userDto) {
    User user = userTransformer.transform(userDto);
    user = userRepository.save(user);
    return userTransformer.transform(user);
  }
}

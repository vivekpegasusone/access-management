package com.drishti.accessmanagement.service.user;

import static com.drishti.accessmanagement.utils.StringUtility.apendTimeInMillis;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.dto.user.UserDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.InvalidRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.user.UserRepository;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.role.Role;
import com.drishti.accessmanagement.repository.entity.user.User;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;
import com.drishti.accessmanagement.service.transformer.role.RoleTransformer;
import com.drishti.accessmanagement.service.transformer.user.UserTransformer;

@Service
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  
  @Autowired
  private UserRepository userRepository;
  
  private Transformer<User, UserDto> userTransformer = new UserTransformer();
  
  private Transformer<Role, RoleDto> roleTransformer = new RoleTransformer();
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public List<UserDto> findAll() {
    logger.info("Retrieving all user records.");
    List<User> userList = userRepository.findAll();

    if (!userList.isEmpty()) {
      return userTransformer.transform(userList);
    } else {
      return new ArrayList<>();
    }
  }
  
  @Override
  public List<UserDto> findActiveUsers() {
    logger.info("Retrieving all active user records.");
    List<User> userList = userRepository.findByActiveTrue();

    if (!userList.isEmpty()) {
      return userTransformer.transform(userList);
    } else {
      return new ArrayList<>();
    }
  }
  
  @Override
  public List<UserDto> findInActiveUsers() {
    logger.info("Retrieving all inactive user records.");
    List<User> userList = userRepository.findByActiveFalse();

    if (!userList.isEmpty()) {
      return userTransformer.transform(userList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public UserDto findUserById(final Long id) throws RecordNotFoundException {
    logger.info("Retrieving user for id {}.", id);
    Optional<User> userOptional = userRepository.findById(id);

    if (userOptional.isPresent()) {
      logger.debug("User record found for id {}.", id);
      return userTransformer.transform(userOptional.get());
    } else {
      logger.debug("User record not found for id {}.", id);
      throw new RecordNotFoundException("No record exist for given user id " + id);
    }
  }

  @Override
  public UserDto findUserByLoginId(final String loginId) throws RecordNotFoundException {
    logger.info("Retrieving user for loginId {}.", loginId);
    Optional<User> userOptional = userRepository.findByLoginId(loginId);

    if (userOptional.isPresent()) {
      logger.info("User record found for loginId {}.", loginId);
      return userTransformer.transform(userOptional.get());
    } else {
      logger.info("User record not found for loginId {}.", loginId);
      throw new RecordNotFoundException("No record exist for given user loginId " + loginId);
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public UserDto createUser(final UserDto userDto) throws DuplicateRecordException {
    if (Objects.nonNull(userDto)) {
      logger.info("Creating user record for user {}.", userDto.getLoginId());
      Optional<User> optionalUser = userRepository.findByLoginId(userDto.getLoginId());
      
      if(optionalUser.isPresent()) {
        throw new DuplicateRecordException("Duplicate record. User loginId already exists.");
      } else {
        User user = userTransformer.transform(userDto);
        user = userRepository.save(user);
        return userTransformer.transform(user);
      }      
    } else {
      logger.info("Can not create user. User information not present.");
      throw new InvalidRecordException("Can not create user. User information not present.");
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public UserDto updateUser(final UserDto userDto) throws DuplicateRecordException, RecordNotFoundException {
    if (Objects.nonNull(userDto) && nonNull(userDto.getId())) {
      logger.info("Updating user record for id {}.", userDto.getId());
      Optional<User> optionalUser =userRepository.findById(userDto.getId());
      
      if (optionalUser.isPresent()) {
        logger.debug("User record found for id {}.", userDto.getId());  
        User user = optionalUser.get();
        
        if(user.getLoginId().equalsIgnoreCase(userDto.getLoginId())) {
          throw new DuplicateRecordException("Duplicate user name. User login id already exists.");
        } else {
          user.setLoginId(userDto.getLoginId());
          user.setEmailId(userDto.getEmailId());
          user.setLastName(userDto.getLastName());
          user.setFirstName(userDto.getFirstName());
          user.setRole(roleTransformer.transform(userDto.getRoleDto()));
          user.setApplication(applicationTransformer.transform(userDto.getApplicationDto()));

          user = userRepository.save(user);
          return userTransformer.transform(user);
        }        
      } else {
        logger.debug("Can not update user. No record not found for user id {}.", userDto.getId());
        throw new RecordNotFoundException("No record exist for given user id " + userDto.getId()); 
      }      
    } else {
      logger.info("Can not update user. User id is empty.");
      throw new InvalidRecordException("Can not update user. User id is empty.");
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteUserById(Long id) throws RecordNotFoundException {
    logger.info("Archiving user record for id {}.", id);
    Optional<User> optionalUser = userRepository.findById(id);

    if (optionalUser.isPresent()) {
      logger.debug("User record found for id {}.", id);
      User user = optionalUser.get();
      user.setActive(false);
      user.setLoginId(apendTimeInMillis(user.getLoginId()));
      userRepository.save(user);
    } else {
      logger.debug("Can not archive user. No record found for user id {}.", id);
      throw new RecordNotFoundException("No record exist for given user id " + id);
    }
  }
}

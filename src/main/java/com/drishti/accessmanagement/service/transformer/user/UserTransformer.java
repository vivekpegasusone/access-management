package com.drishti.accessmanagement.service.transformer.user;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.dto.user.UserDto;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.role.Role;
import com.drishti.accessmanagement.repository.entity.user.User;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;

public class UserTransformer implements Transformer<User, UserDto> {
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();
  
  @Override
  public UserDto transform(User user) {
    UserDto userDto = null;
    if (Objects.nonNull(user)) {
      ApplicationDto appDto = applicationTransformer.transform(user.getApplication());
      RoleDto roleDto = transformToRoleDto(user.getRole());

      userDto = new UserDto(user.getId(), user.getLoginId(), user.getPassword(), user.getFirstName(),
          user.getLastName(), user.getEmailId(), user.isActive());
      userDto.setRoleDto(roleDto);
      userDto.setApplicationDto(appDto);
    }
    return userDto;
  }

  @Override
  public List<UserDto> transform(List<User> users) {
    List<UserDto> userDtoList;
    
    if(CollectionUtils.isEmpty(users)) {
      userDtoList = Collections.emptyList();
    } else {
      userDtoList = users.stream().map(u -> transform(u)).collect(Collectors.toList());
    }
    
    return userDtoList;
  }

  @Override
  public User transform(UserDto userDto) {
    User user = null;
    if (Objects.nonNull(userDto)) {
      Role role = transformToRole(userDto.getRoleDto());
      Application application = applicationTransformer.transform(userDto.getApplicationDto());

      user = new User.UserBuilder(userDto.getLoginId()).setId(userDto.getId()).setFirstName(userDto.getFirstName())
          .setLastName(userDto.getLastName()).setEmailId(userDto.getEmailId()).setPassword(userDto.getPassword())
          .setActive(userDto.isActive()).setRole(role).setApplication(application).build();
    }
    return user;
  }
  
  private Role transformToRole(RoleDto roleDto) {
    Role role = null;
    if (Objects.nonNull(roleDto)) {
      role = new Role.RoleBuilder(roleDto.getName()).setId(roleDto.getId()).setDescription(roleDto.getDescription())
          .setActive(roleDto.isActive()).build();
    }
    return role;
  }

  private RoleDto transformToRoleDto(Role role) {
    RoleDto roleDto = null;
    if (Objects.nonNull(role)) {
      roleDto = new RoleDto(role.getId(), role.getName(), role.getDescription(), role.isActive());
      roleDto.setCreatedBy(role.getCreatedBy());
      roleDto.setCreatedOn(role.getCreatedOn());
      roleDto.setUpdatedBy(role.getUpdatedBy());
      roleDto.setUpdatedOn(role.getUpdatedOn());
    }
    return roleDto;
  }
}
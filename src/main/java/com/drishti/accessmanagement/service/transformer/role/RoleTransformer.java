package com.drishti.accessmanagement.service.transformer.role;

import java.util.Collections;
import java.util.List;
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

public class RoleTransformer implements Transformer<Role, RoleDto> {

  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();
  
  @Override
  public RoleDto transform(Role role) {
    ApplicationDto appDto = applicationTransformer.transform(role.getApplication());
    List<UserDto> userDtos = transformToUserDto(role.getUsers());
       
    RoleDto roleDto = new RoleDto(role.getId(), role.getName(), role.getDescription(), role.isActive());
    roleDto.setCreatedBy(role.getCreatedBy());
    roleDto.setCreatedOn(role.getCreatedOn());
    roleDto.setUpdatedBy(role.getUpdatedBy());
    roleDto.setUpdatedOn(role.getUpdatedOn());
    
    roleDto.setApplicationDto(appDto);   
    roleDto.setUserDtos(userDtos);

    return roleDto;
  }

  @Override
  public List<RoleDto> transform(List<Role> roles) {    
    List<RoleDto> roleDtos;
    
    if(CollectionUtils.isEmpty(roles)) {
      roleDtos = Collections.emptyList();
    } else {
      roleDtos = roles.stream().map(r -> transform(r)).collect(Collectors.toList());
    }
    
    return roleDtos;
  }

  @Override
  public Role transform(RoleDto roleDto) {
    Role role = new Role.RoleBuilder(roleDto.getName()).setId(roleDto.getId()).setDescription(roleDto.getDescription())
        .setActive(roleDto.isActive()).build();
    
    Application app = applicationTransformer.transform(roleDto.getApplicationDto());
    
    List<User> users = roleDto.getUserDtos().parallelStream().map(u -> new User.UserBuilder(u.getLoginId()).setId(u.getId())
        .setFirstName(u.getFirstName()).setLastName(u.getLastName()).setEmailId(u.getEmailId()).setActive(u.isActive())
        .build()).collect(Collectors.toList());
        
    role.setApplication(app);
    role.setUsers(users);

    return role;
  }
  
  private List<UserDto> transformToUserDto(List<User> users) {
    List<UserDto> userDtoList;

    if (CollectionUtils.isEmpty(users)) {
      userDtoList = Collections.emptyList();
    } else {
      userDtoList = users.stream().map(u -> new UserDto(u.getId(), u.getLoginId(), u.getPassword(),
          u.getFirstName(), u.getLastName(), u.getEmailId(), u.isActive())).collect(Collectors.toList());
    }

    return userDtoList;
  }

}

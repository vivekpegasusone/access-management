package com.drishti.accessmanagement.service.transformer.role;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.permission.PermissionDto;
import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.dto.user.UserDto;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.permission.Permission;
import com.drishti.accessmanagement.repository.entity.role.Role;
import com.drishti.accessmanagement.repository.entity.user.User;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;
import com.drishti.accessmanagement.service.transformer.permission.PermissionTransformer;

public class RoleTransformer implements Transformer<Role, RoleDto> {

  private static final Logger logger = LoggerFactory.getLogger(RoleTransformer.class);
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();
  
  @Override
  public RoleDto transform(Role role) {
    logger.info("Transforming role entity to role dto.");
    RoleDto roleDto = null;
    if (Objects.nonNull(role)) {
      ApplicationDto appDto = applicationTransformer.transform(role.getApplication());
      List<UserDto> userDtos = transformToUserDto(role.getUsers());

      Transformer<Permission, PermissionDto> permissionTransformer = new PermissionTransformer();
      PermissionDto permissionDto = permissionTransformer.transform(role.getPermission());
      
      roleDto = new RoleDto(role.getId(), role.getName(), role.getDescription(), role.isActive());
      roleDto.setCreatedBy(role.getCreatedBy());
      roleDto.setCreatedOn(role.getCreatedOn());
      roleDto.setUpdatedBy(role.getUpdatedBy());
      roleDto.setUpdatedOn(role.getUpdatedOn());

      roleDto.setApplicationDto(appDto);
      roleDto.setPermissionDto(permissionDto);
      roleDto.setUserDtos(userDtos);
    }
    return roleDto;
  }

  @Override
  public List<RoleDto> transform(List<Role> roles) {   
    logger.info("Transforming role entitys to role dtos.");
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
    logger.info("Transforming role dto to role entity.");
    Role role = null;
    if (Objects.nonNull(roleDto)) {
      Application app = applicationTransformer.transform(roleDto.getApplicationDto());
      
      Transformer<Permission, PermissionDto> permissionTransformer = new PermissionTransformer();
      Permission permission = permissionTransformer.transform(roleDto.getPermissionDto());

      List<User> users = roleDto.getUserDtos().stream()
          .map(u -> new User.UserBuilder(u.getLoginId()).setId(u.getId()).setFirstName(u.getFirstName())
              .setLastName(u.getLastName()).setEmailId(u.getEmailId()).setActive(u.isActive()).build())
          .collect(Collectors.toList());

      role = new Role.RoleBuilder(roleDto.getName()).setId(roleDto.getId()).setDescription(roleDto.getDescription())
          .setActive(roleDto.isActive()).setApplication(app).setPermission(permission).setUsers(users).build();
    }
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

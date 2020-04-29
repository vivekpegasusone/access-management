package com.drishti.accessmanagement.service.transformer.permission;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.permission.PermissionDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;
import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.permission.Permission;
import com.drishti.accessmanagement.repository.entity.resource.Resource;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.action.ActionTransformer;
import com.drishti.accessmanagement.service.transformer.resource.ResourceTransformer;

public class PermissionTransformer  implements Transformer<Permission, PermissionDto> {

  private Transformer<Action, ActionDto> actionTransformer = new ActionTransformer();
  
  private Transformer<Resource, ResourceDto> resourceTransformer = new ResourceTransformer();
  
  @Override
  public PermissionDto transform(Permission permission) {
    PermissionDto permissionDto = null;
    if (Objects.nonNull(permission)) {
      permissionDto = new PermissionDto(permission.getId(), permission.getName(), permission.getDescription(),
          permission.isActive());
      permissionDto.setCreatedBy(permission.getCreatedBy());
      permissionDto.setCreatedOn(permission.getCreatedOn());
      permissionDto.setUpdatedBy(permission.getUpdatedBy());
      permissionDto.setUpdatedOn(permission.getUpdatedOn());

      ActionDto actionDto = actionTransformer.transform(permission.getAction());
      ResourceDto resourceDto = resourceTransformer.transform(permission.getResource());
      
      permissionDto.setActionDto(actionDto);
      permissionDto.setResourceDto(resourceDto);
    }
    return permissionDto;
  }

  @Override
  public Permission transform(PermissionDto permissionDto) {
    Permission permission = null;
    if (Objects.nonNull(permissionDto)) {
      Action action = actionTransformer.transform(permissionDto.getActionDto());
      Resource resource = resourceTransformer.transform(permissionDto.getResourceDto());

      permission = new Permission.PermissionBuilder(permissionDto.getName()).setId(permissionDto.getId())
          .setDescription(permissionDto.getDescription()).setActive(permissionDto.isActive()).setAction(action)
          .setResource(resource).build();
    }
    return permission;
  }

  @Override
  public List<PermissionDto> transform(List<Permission> permissions) {
    List<PermissionDto> permissionDtos;

    if (CollectionUtils.isEmpty(permissions)) {
      permissionDtos = Collections.emptyList();
    } else {
      permissionDtos = permissions.stream().map(p -> transform(p)).collect(Collectors.toList());
    }

    return permissionDtos;
  }
  
}

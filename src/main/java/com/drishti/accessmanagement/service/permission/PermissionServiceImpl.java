package com.drishti.accessmanagement.service.permission;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.permission.PermissionDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.permission.PermissionRepository;
import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.permission.Permission;
import com.drishti.accessmanagement.repository.entity.resource.Resource;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.action.ActionTransformer;
import com.drishti.accessmanagement.service.transformer.permission.PermissionTransformer;
import com.drishti.accessmanagement.service.transformer.resource.ResourceTransformer;

@Service
class PermissionServiceImpl implements PermissionService {

  @Autowired
  private PermissionRepository permissionRepository;

  private Transformer<Action, ActionDto> actionTransformer = new ActionTransformer();

  private Transformer<Resource, ResourceDto> resourceTransformer = new ResourceTransformer();

  private Transformer<Permission, PermissionDto> permissionTransformer = new PermissionTransformer();

  @Override
  public List<PermissionDto> findAll() {
    List<Permission> permissions = permissionRepository.findAll();
    return permissionTransformer.transform(permissions);
  }

  @Override
  public List<PermissionDto> findActivePermissions() {
    List<Permission> permissionList = permissionRepository.findByActiveTrue();

    if (!permissionList.isEmpty()) {
      return permissionTransformer.transform(permissionList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public List<PermissionDto> findInActivePermissions() {
    List<Permission> permissionList = permissionRepository.findByActiveFalse();

    if (!permissionList.isEmpty()) {
      return permissionTransformer.transform(permissionList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public PermissionDto findPermissionById(Long id) throws RecordNotFoundException {
    Optional<Permission> optionalPermission = permissionRepository.findById(id);

    if (optionalPermission.isPresent()) {
      return permissionTransformer.transform(optionalPermission.get());
    } else {
      throw new RecordNotFoundException("No record exist for given permission id " + id);
    }
  }

  @Override
  public PermissionDto createPermission(PermissionDto permissionDto) {
    Permission permission = permissionTransformer.transform(permissionDto);
    permission = permissionRepository.save(permission);
    return permissionTransformer.transform(permission);
  }

  @Override
  public PermissionDto updatePermission(PermissionDto permissionDto) throws RecordNotFoundException {
    Permission permission = permissionRepository.findById(permissionDto.getId()).get();
    permission.setName(permissionDto.getName());
    permission.setDescription(permissionDto.getDescription());
    permission.setAction(actionTransformer.transform(permissionDto.getActionDto()));
    permission.setResource(resourceTransformer.transform(permissionDto.getResourceDto()));

    permissionRepository.save(permission);
    return permissionTransformer.transform(permission);
  }

  @Override
  public void deletePermissionById(Long id) throws RecordNotFoundException {
    Permission permission = permissionRepository.findById(id).get();
    permission.setActive(false);
    // set all permission/mapping to false
    permissionRepository.save(permission);
  }

  @Override
  public List<PermissionDto> findPermissionsByApplicationId(Long applicationId) {
    List<Permission> permissions = permissionRepository.findPermissionsByApplicationId(applicationId);
    return permissionTransformer.transform(permissions);
  }

}

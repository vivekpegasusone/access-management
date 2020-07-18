package com.drishti.accessmanagement.service.permission;

import static com.drishti.accessmanagement.utils.StringUtility.apendTimeInMillis;
import static java.util.Objects.nonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.permission.PermissionDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.InvalidRecordException;
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

  private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
  
  @Autowired
  private PermissionRepository permissionRepository;

  private Transformer<Action, ActionDto> actionTransformer = new ActionTransformer();

  private Transformer<Resource, ResourceDto> resourceTransformer = new ResourceTransformer();

  private Transformer<Permission, PermissionDto> permissionTransformer = new PermissionTransformer();

  @Override
  public List<PermissionDto> findAll() {
    logger.info("Retrieving all permission records.");
    List<Permission> permissions = permissionRepository.findAll();
    return permissionTransformer.transform(permissions);
  }

  @Override
  public List<PermissionDto> findActivePermissions() {
    logger.info("Retrieving all active permission records.");
    List<Permission> permissionList = permissionRepository.findByActiveTrue();

    if (!permissionList.isEmpty()) {
      return permissionTransformer.transform(permissionList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public List<PermissionDto> findInActivePermissions() {
    logger.info("Retrieving all inactive permission records.");
    List<Permission> permissionList = permissionRepository.findByActiveFalse();

    if (!permissionList.isEmpty()) {
      return permissionTransformer.transform(permissionList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public PermissionDto findPermissionById(Long id) throws RecordNotFoundException {
    logger.info("Retrieving permission for id {}.", id);
    Optional<Permission> optionalPermission = permissionRepository.findById(id);

    if (optionalPermission.isPresent()) {
      logger.debug("Permission record found for id {}.", id);
      return permissionTransformer.transform(optionalPermission.get());
    } else {
      logger.debug("Permission record found for id {}.", id);
      throw new RecordNotFoundException("No record exist for given permission id " + id);
    }
  }

  @Override
  public PermissionDto createPermission(PermissionDto permissionDto) throws DuplicateRecordException {
    if (nonNull(permissionDto) && nonNull(permissionDto.getResourceDto()) && nonNull(permissionDto.getActionDto())) {
      logger.info("Creating permission record for permission {}.", permissionDto.getName());

      Optional<Permission> optionalPermission = permissionRepository.findByNameOrResourceAndAction(
          permissionDto.getName(), permissionDto.getResourceDto().getId(), permissionDto.getActionDto().getId());

      if (optionalPermission.isPresent()) {
        Permission permission = optionalPermission.get();
        if(permission.getName().equalsIgnoreCase(permissionDto.getName())) {
          throw new DuplicateRecordException("Duplicate record. Permission name already exists.");
        } else {
          throw new DuplicateRecordException("Duplicate record. Permission already exists with same resource and action.");
        }
      } else {
        Permission permission = permissionTransformer.transform(permissionDto);
        permission = permissionRepository.save(permission);
        return permissionTransformer.transform(permission);
      }
    } else {
      logger.info("Can not create permission. Permission information not present.");
      throw new InvalidRecordException("Can not create permission. Permission information not present.");
    }
  }

  @Override
  public PermissionDto updatePermission(PermissionDto permissionDto) throws RecordNotFoundException, DuplicateRecordException {
    if (Objects.nonNull(permissionDto) && nonNull(permissionDto.getId())) {
      logger.info("Updating permission record for id {}.", permissionDto.getId());
      Optional<Permission> optionalPermission = permissionRepository.findById(permissionDto.getId());

      if (optionalPermission.isPresent()) {
        logger.debug("Permission record found for id {}.", permissionDto.getId());
        Permission permission = optionalPermission.get();

        if (permission.getName().equalsIgnoreCase(permissionDto.getName())) {
          throw new DuplicateRecordException("Duplicate permission name. Permission name already exists.");
        } else if (permission.getResource().getId() == permissionDto.getResourceDto().getId()
            && permission.getAction().getId() == permissionDto.getActionDto().getId()) {
          throw new DuplicateRecordException("Duplicate permission. Permission already exists with same resource and action.");
        } else {
          permission.setName(permissionDto.getName());
          permission.setDescription(permissionDto.getDescription());
          permission.setAction(actionTransformer.transform(permissionDto.getActionDto()));
          permission.setResource(resourceTransformer.transform(permissionDto.getResourceDto()));

          permissionRepository.save(permission);
          return permissionTransformer.transform(permission);
        }
      } else {
        logger.debug("Can not update permission. No record found for permission id {}.", permissionDto.getId());
        throw new RecordNotFoundException("No record exist for given permission id " + permissionDto.getId());
      }
    } else {
      logger.info("Can not update permission. Permission id is empty.");
      throw new InvalidRecordException("Can not update permission. Permission id is empty.");
    }
  }

  @Override
  public void deletePermissionById(Long id) throws RecordNotFoundException {
    logger.info("Archiving permission record for id {}.", id);
    Optional<Permission> optionalPermission = permissionRepository.findById(id);

    if (optionalPermission.isPresent()) {
      logger.debug("Permission record found for id {}.", id);
      Permission permission = optionalPermission.get();
      permission.setActive(false);
      permission.setName(apendTimeInMillis(permission.getName()));
      permission.setResource(null);
      permission.setAction(null);
      // set all permission/mapping to false
      permissionRepository.save(permission);
    } else {
      logger.debug("Can not archive permission. No record found for permission id {}.", id);
      throw new RecordNotFoundException("No record exist for given permission id " + id);
    }
  }

  @Override
  public List<PermissionDto> findPermissionsByApplicationId(Long applicationId) {
    logger.info("Retrieving permissions for application id {}.", applicationId);
    List<Permission> permissions = permissionRepository.findPermissionsByApplicationId(applicationId);
    return permissionTransformer.transform(permissions);
  }

}

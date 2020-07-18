package com.drishti.accessmanagement.service.permission;

import java.util.List;

import com.drishti.accessmanagement.dto.permission.PermissionDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

public interface PermissionService {
  
  List<PermissionDto> findAll();

  List<PermissionDto> findActivePermissions();

  List<PermissionDto> findInActivePermissions();

  PermissionDto findPermissionById(Long id) throws RecordNotFoundException;

  PermissionDto createPermission(PermissionDto permissionDto) throws DuplicateRecordException;

  PermissionDto updatePermission(PermissionDto permissionDto) throws RecordNotFoundException, DuplicateRecordException;

  void deletePermissionById(Long id) throws RecordNotFoundException;

  List<PermissionDto> findPermissionsByApplicationId(Long applicationId);
}

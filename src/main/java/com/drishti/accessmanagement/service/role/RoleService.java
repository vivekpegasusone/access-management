package com.drishti.accessmanagement.service.role;

import java.util.List;

import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

public interface RoleService {

  List<RoleDto> findAll();
  
  List<RoleDto> findActiveRoles();
  
  List<RoleDto> findInActiveRoles();  

  RoleDto findRoleById(Long id) throws RecordNotFoundException;

  RoleDto createRole(RoleDto roleDto) throws DuplicateRecordException;

  RoleDto updateRole(RoleDto roleDto) throws RecordNotFoundException, DuplicateRecordException;

  void deleteRoleById(Long id) throws RecordNotFoundException;

  List<RoleDto> findRolesByApplicationId(Long applicationId);
}

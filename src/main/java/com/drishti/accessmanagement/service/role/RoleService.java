package com.drishti.accessmanagement.service.role;

import com.drishti.accessmanagement.dto.role.RoleView;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

import java.util.List;

public interface RoleService {

  List<RoleView> getRoles();

  RoleView findRoleById(Long id) throws RecordNotFoundException;

  RoleView createRole(RoleView roleView);

  RoleView updateRole(RoleView roleView) throws RecordNotFoundException;

  void deleteRoleById(Long id) throws RecordNotFoundException;

}

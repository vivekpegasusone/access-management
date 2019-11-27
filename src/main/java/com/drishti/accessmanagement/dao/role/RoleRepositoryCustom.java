package com.drishti.accessmanagement.dao.role;

import com.drishti.accessmanagement.entity.role.Role;

import java.util.List;

interface RoleRepositoryCustom {

  List<Role> getAllActiveRoles();

  List<Role> getAllInActiveRoles();

}

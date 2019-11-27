package com.drishti.accessmanagement.service.permission;

import com.drishti.accessmanagement.dao.permission.PermissionMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PermissionMappingServiceImpl {

  @Autowired
  private PermissionMappingRepository permissionMappingRepository;
}

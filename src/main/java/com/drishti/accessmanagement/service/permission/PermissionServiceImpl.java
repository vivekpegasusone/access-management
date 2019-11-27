package com.drishti.accessmanagement.service.permission;

import com.drishti.accessmanagement.dao.permission.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PermissionServiceImpl implements PermissionService {

  @Autowired
  private PermissionRepository userRepository;
}

package com.drishti.accessmanagement.service.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.repository.dao.permission.PermissionRepository;

@Service
class PermissionServiceImpl implements PermissionService {

  @Autowired
  private PermissionRepository userRepository;
}

package com.drishti.accessmanagement.service.role;

import com.drishti.accessmanagement.dao.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository userRepository;
}

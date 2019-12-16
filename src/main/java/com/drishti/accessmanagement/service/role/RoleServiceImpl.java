package com.drishti.accessmanagement.service.role;

import com.drishti.accessmanagement.dao.role.RoleRepository;
import com.drishti.accessmanagement.dto.role.RoleView;
import com.drishti.accessmanagement.entity.role.Role;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.drishti.accessmanagement.utils.RoleUtility.*;

@Service
class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public List<RoleView> getRoles() {
    List<Role> roleList = roleRepository.findByActiveTrue();

    if (!roleList.isEmpty()) {
      return prepareRoleViewsFromRoles(roleList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public RoleView findRoleById(Long id) {
    Optional<Role> optionalRole = roleRepository.findById(id);

    if (optionalRole.isPresent()) {
      return prepareRoleViewFromRole(optionalRole.get());
    } else {
      throw new RecordNotFoundException("No record exist for given role id " + id);
    }
  }

  @Override
  @Transactional(propagation= Propagation.REQUIRED)
  public RoleView createRole(RoleView roleView) {
    return saveOrUpdateRole(roleView);
  }

  @Override
  @Transactional(propagation= Propagation.REQUIRED)
  public RoleView updateRole(RoleView roleView) {
    return saveOrUpdateRole(roleView);
  }

  @Override
  public void deleteRoleById(Long id) {
    roleRepository.deleteById(id);
  }

  private RoleView saveOrUpdateRole(final RoleView roleView) {
    Role role = prepareRoleFromRoleView(roleView);
    Role savedRole = roleRepository.save(role);
    return prepareRoleViewFromRole(savedRole);
  }
}

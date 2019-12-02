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

import static com.drishti.accessmanagement.utils.BeanUtility.copy;
import static org.springframework.beans.BeanUtils.copyProperties;

@Service
class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;


  @Override
  public List<RoleView> getRoles() {
    List<Role> roleList = roleRepository.findByActiveTrue();

    if (roleList.size() > 0) {
      return copy(roleList, new ArrayList<>(), RoleView.class);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public RoleView findRoleById(Long id) throws RecordNotFoundException {
    Optional<Role> optionalRole = roleRepository.findById(id);

    if (optionalRole.isPresent()) {
      return prepareRoleView(optionalRole.get());
    } else {
      throw new RecordNotFoundException("No record exist for given user id " + id);
    }
  }

  @Override
  @Transactional(propagation= Propagation.REQUIRED)
  public RoleView createRole(RoleView roleView) {
    return saveOrUpdateRole(roleView);
  }

  @Override
  @Transactional(propagation= Propagation.REQUIRED)
  public RoleView updateRole(RoleView roleView) throws RecordNotFoundException {
    return saveOrUpdateRole(roleView);
  }

  @Override
  public void deleteRoleById(Long id) throws RecordNotFoundException {
    roleRepository.deleteById(id);
  }

  private RoleView prepareRoleView(final Role role) {
    RoleView roleView = new RoleView();
    copyProperties(role, roleView);
    return roleView;
  }

  private RoleView saveOrUpdateRole(RoleView roleView) {
    Role role = new Role();
    copyProperties(roleView, role);

    Role savedUser = roleRepository.save(role);

    RoleView rv = new RoleView();
    copyProperties(savedUser, rv);
    return rv;
  }
}

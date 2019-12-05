package com.drishti.accessmanagement.service.role;

import com.drishti.accessmanagement.dao.role.RoleRepository;
import com.drishti.accessmanagement.dto.role.RoleView;
import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.entity.role.Role;
import com.drishti.accessmanagement.entity.user.User;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Autowired
  RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }


  @Override
  public List<RoleView> getRoles() {
    List<Role> roleList = roleRepository.findByActiveTrue();

    if (roleList.size() > 0) {
      return prepareRoleViewsFromRoles(roleList);
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
    return prepareRoleViewFromRole(role);
  }

  private RoleView saveOrUpdateRole(final RoleView roleView) {
    Role role = prepareRoleFromRoleView(roleView);
    Role savedRole = roleRepository.save(role);
    return prepareRoleViewFromRole(savedRole);
  }

  private List<RoleView> prepareRoleViewsFromRoles(final List<Role> roles) {
    List<RoleView> roleViews = new ArrayList<>(roles.size());

    roles.forEach(r -> {
      RoleView roleView = prepareRoleViewFromRole(r);
      roleViews.add(roleView);
    });

    return roleViews;
  }

  private Role prepareRoleFromRoleView(RoleView rv) {
    Role role = new Role(rv.getId(), rv.getName(), rv.getDescription(), rv.isActive());

    List<UserView> userViews = rv.getUsers();
    List<User> users = new ArrayList<>(userViews.size());

    userViews.forEach(u -> {
      User user = new User(u.getId(), u.getLoginId(), u.getFirstName(), u.getLastName(), u.getEmailId(), u.isActive());
      users.add(user);
    });

    role.setUsers(users);

    return role;
  }

  private RoleView prepareRoleViewFromRole(Role role) {
    RoleView roleView = new RoleView(role.getId(), role.getName(), role.getDescription(), role.isActive());

    List<User> users = role.getUsers();
    List<UserView> userViews = new ArrayList<>(users.size());

    users.forEach(user -> {
      UserView userView = new UserView( user.getId(), user.getLoginId(), user.getFirstName(), user.getLastName(),
          user.getEmailId(), user.isActive());
      userViews.add(userView);
    });

    roleView.setUsers(userViews);

    return roleView;
  }
}

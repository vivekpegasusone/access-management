package com.drishti.accessmanagement.service.user;

import com.drishti.accessmanagement.dao.user.UserRepository;
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
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<UserView> getUsers() {
    List<User> userList = userRepository.findByActiveTrue();

    if (userList.size() > 0) {
      return prepareUserViewsFromUsers(userList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public UserView findUserById(final Long id) throws RecordNotFoundException {
    Optional<User> userOptional = userRepository.findById(id);

    if (userOptional.isPresent()) {
      return prepareUserView(userOptional.get());
    } else {
      throw new RecordNotFoundException("No record exist for given user id " + id);
    }
  }

  @Override
  public UserView findUserByLoginId(final String loginId) throws RecordNotFoundException {
    Optional<User> userOptional = userRepository.findByLoginId(loginId);

    if (userOptional.isPresent()) {
      return prepareUserView(userOptional.get());
    } else {
      throw new RecordNotFoundException("No record exist for given user loginId " + loginId);
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public UserView createUser(final UserView userView) {
    return saveOrUpdateUser(userView);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public UserView updateUser(final UserView userView) throws RecordNotFoundException {
    return saveOrUpdateUser(userView);
  }

  @Override
  public void deleteUserById(Long id) throws RecordNotFoundException {
    userRepository.deleteById(id);
  }

  private UserView prepareUserView(final User user) {
    return prepareUserViewFromUser(user);
  }

  private UserView saveOrUpdateUser(final UserView userView) {
    User user = prepareUserFromUserView(userView);
    User savedUser = userRepository.save(user);
    return prepareUserViewFromUser(savedUser);
  }

  private List<UserView> prepareUserViewsFromUsers(final List<User> users) {
    List<UserView> userViews = new ArrayList<>(users.size());

    users.forEach(u -> {
      UserView userView = prepareUserViewFromUser(u);
      userViews.add(userView);
    });

    return userViews;
  }

  private UserView prepareUserViewFromUser(final User user) {

    UserView userView = new UserView(user.getId(), user.getLoginId(), user.getFirstName(), user.getLastName(),
        user.getEmailId(), user.isActive());

    List<Role> roles = user.getRoles();
    List<RoleView> roleViews = new ArrayList<>(roles.size());

    roles.forEach(r -> {
      RoleView roleView = new RoleView(r.getId(), r.getName(), r.getDescription(), r.isActive());
      roleViews.add(roleView);
    });

    userView.setRoles(roleViews);

    return userView;
  }

  private User prepareUserFromUserView(final UserView uv) {

    User user = new User(uv.getId(), uv.getLoginId(), uv.getFirstName(), uv.getLastName(), uv.getEmailId(),
        uv.isActive());

    List<RoleView> roleViews = uv.getRoles();
    List<Role> roles = new ArrayList<>(roleViews.size());

    roleViews.forEach(r -> {
      Role role = new Role(r.getId(), r.getName(), r.getDescription(), r.isActive());
      roles.add(role);
    });

    user.setRoles(roles);

    return user;
  }
}

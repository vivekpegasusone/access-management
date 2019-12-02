package com.drishti.accessmanagement.service.user;

import com.drishti.accessmanagement.dao.user.UserRepository;
import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.entity.user.User;
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
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<UserView> getUsers() {
    List<User> userList = userRepository.findByActiveTrue();

    if (userList.size() > 0) {
      return copy(userList, new ArrayList<>(), UserView.class);
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
  @Transactional(propagation= Propagation.REQUIRED)
  public UserView createUser(final UserView userView) {
    return saveOrUpdateUser(userView);
  }

  @Override
  @Transactional(propagation= Propagation.REQUIRED)
  public UserView updateUser(final UserView userView) throws RecordNotFoundException {
    return saveOrUpdateUser(userView);
  }

  @Override
  public void deleteUserById(Long id) throws RecordNotFoundException {
    userRepository.deleteById(id);
  }

  private UserView prepareUserView(final User user) {
    UserView userView = new UserView();
    copyProperties(user, userView);
    return userView;
  }

  private UserView saveOrUpdateUser(final UserView userView) {
    User user = new User();
    copyProperties(userView, user);

    User savedUser = userRepository.save(user);

    UserView uv = new UserView();
    copyProperties(savedUser, uv);
    return uv;
  }
}

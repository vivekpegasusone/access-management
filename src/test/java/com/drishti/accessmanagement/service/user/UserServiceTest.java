package com.drishti.accessmanagement.service.user;

import com.drishti.accessmanagement.dao.user.UserRepository;
import com.drishti.accessmanagement.dto.role.RoleView;
import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.entity.role.Role;
import com.drishti.accessmanagement.entity.user.User;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.drishti.accessmanagement.utility.UserFixture.anyUser;
import static com.drishti.accessmanagement.utils.UserUtility.prepareUserViewFromUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private List<User> users;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService = new UserServiceImpl();

  @BeforeEach
  public void setup() {
    users = new ArrayList<>();
    users.add(anyUser());
    users.add(anyUser());
  }

  @Test
  public void getUsers() {
    when(userRepository.findByActiveTrue()).thenReturn(users);

    List<UserView> userViews = userService.findActiveUsers();
    assertThat(userViews).isNotEmpty();
    assertThat(userViews).hasSize(2);
    compareUsers(userViews, users);

    users.clear();
    userViews = userService.findActiveUsers();
    assertThat(userViews).isEmpty();
    assertThat(userViews).hasSize(0);
  }

  @Test
  public void getUsersWhenNoUserExists() {
    when(userRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

    List<UserView> userViews = userService.findActiveUsers();
    assertThat(userViews).isEmpty();
  }

  @Test
  public void findUserById() {
    User user = users.get(0);
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    UserView userView = userService.findUserById(user.getId());
    assertThat(userView).isNotNull();
    compareUser(userView, user);
  }

  @Test
  public void findUserByInvalidId() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());
    RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
      userService.findUserById(1L)
    );

    assertEquals("No record exist for given user id 1", exception.getMessage());
  }

  @Test
  public void findUserByLoginId() {
    User user = users.get(0);
    when(userRepository.findByLoginId(user.getLoginId())).thenReturn(Optional.of(user));

    UserView userView = userService.findUserByLoginId(user.getLoginId());
    assertThat(userView).isNotNull();
    compareUser(userView, user);
  }

  @Test
  public void findUserByInvalidLoginId() {
    when(userRepository.findByLoginId("test")).thenReturn(Optional.empty());
    RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
      userService.findUserByLoginId("test")
    );

    assertEquals("No record exist for given user loginId test", exception.getMessage());
  }

  @Test
  public void createUser() {
    User user = users.get(0);
    when(userRepository.save(user)).thenReturn(user);

    UserView userView = userService.createUser(prepareUserViewFromUser(user));
    assertThat(userView).isNotNull();
    compareUser(userView, user);
  }

  @Test
  public void updateUser() {
    String newName = "anyName";
    User user = users.get(0);
    user.setFirstName(newName);
    when(userRepository.save(user)).thenReturn(user);

    UserView userView = userService.updateUser(prepareUserViewFromUser(user));
    assertThat(userView).isNotNull();
    assertThat(userView.getFirstName()).isEqualTo(newName);
    compareUser(userView, user);
  }

  @Test
  public void deleteUserById() {
    User user = users.get(0);
    userService.deleteUserById(user.getId());
    verify(userRepository, times(1)).deleteById(user.getId());
  }

  private void compareUsers(List<UserView> userViews, List<User> users) {
    for (int i = 0; i < userViews.size(); i++) {
      UserView uv = userViews.get(i);
      User u = users.get(i);
      compareUser(uv, u);
    }
  }

  private void compareUser(UserView uv, User u) {
    assertThat(uv.getId()).isEqualTo(u.getId());
    assertThat(uv.getLoginId()).isEqualTo(u.getLoginId());
    assertThat(uv.getFirstName()).isEqualTo(u.getFirstName());
    assertThat(uv.getLastName()).isEqualTo(u.getLastName());
    assertThat(uv.getEmailId()).isEqualTo(u.getEmailId());
    assertThat(uv.isActive()).isEqualTo(u.isActive());

    for (int i = 0; i < uv.getRoleViews().size(); i++) {
      RoleView rv = uv.getRoleViews().get(i);
      Role r = u.getRoles().get(i);
      compareRoleState(rv, r);
    }
  }

  private void compareRoleState(RoleView rv, Role r) {
    assertThat(rv.getId()).isEqualTo(r.getId());
    assertThat(rv.getName()).isEqualTo(r.getName());
    assertThat(rv.getDescription()).isEqualTo(r.getDescription());
    assertThat(rv.isActive()).isEqualTo(r.isActive());
  }
}

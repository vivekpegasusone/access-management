package com.drishti.accessmanagement.service.user;

import com.drishti.accessmanagement.dao.user.UserRepository;
import com.drishti.accessmanagement.dto.role.RoleView;
import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.entity.role.Role;
import com.drishti.accessmanagement.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.drishti.accessmanagement.utility.UserFixture.anyUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
  void getUsers() {

    when(userRepository.findByActiveTrue()).thenReturn(users);

    List<UserView> userViews = userService.getUsers();
    assertThat(userViews).isNotEmpty();
    assertThat(userViews).hasSize(2);
    compareUsers(userViews, users);

    users.clear();
    userViews = userService.getUsers();
    assertThat(userViews).isEmpty();
    assertThat(userViews).hasSize(0);
  }

  @Test
  void findUserById() {
  }

  @Test
  void findUserByLoginId() {
  }

  @Test
  void createUser() {
  }

  @Test
  void updateUser() {
  }

  @Test
  void deleteUserById() {
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

    for (int i = 0; i < uv.getRoles().size(); i++) {
      RoleView rv = uv.getRoles().get(i);
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

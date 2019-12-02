package com.drishti.accessmanagement.service.user;

import com.drishti.accessmanagement.dao.user.UserRepository;
import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static com.drishti.accessmanagement.utility.UserFixture.anyUser;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

  private List<User> users;

  @MockBean
  private UserRepository userRepository = Mockito.mock(UserRepository.class);

  @Autowired
  private UserService userService;

  @BeforeEach
  public void setup() {
    users = new ArrayList<>();
    users.add(anyUser());
    users.add(anyUser());
    Mockito.when(userRepository.findByActiveTrue()).thenReturn(users);
  }

  @Test
  void getUsers() {
    List<UserView> userView = userService.getUsers();
    assertThat(userView).isNotEmpty();
    assertThat(userView).hasSize(2);
    assertThat(userView).element(0).isEqualTo(users.get(0));
    assertThat(userView).element(1).isEqualTo(users.get(1));

    users.clear();
    userView = userService.getUsers();
    assertThat(userView).isEmpty();
    assertThat(userView).hasSize(0);
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
}

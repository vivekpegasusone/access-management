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
    compareUsers(userViews, users);

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
  
  private void compareUsers(List<UserView> userViews, List<User> users) {
		for(int i = 0; i < userViews.size(); i++) {
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
		
		for(int i = 0; i < uv.getRoles().size(); i++) {
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

package com.drishti.accessmanagement.service.user;

import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.dto.user.UserDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.user.UserRepository;
import com.drishti.accessmanagement.repository.entity.role.Role;
import com.drishti.accessmanagement.repository.entity.user.User;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.user.UserTransformer;

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

  private Transformer<User, UserDto> userTransformer = new UserTransformer();
  
  @BeforeEach
  public void setup() {
    users = new ArrayList<>();
    users.add(anyUser());
    users.add(anyUser());
  }

  @Test
  public void getUsers() {
    when(userRepository.findByActiveTrue()).thenReturn(users);

    List<UserDto> userViews = userService.findActiveUsers();
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

    List<UserDto> userViews = userService.findActiveUsers();
    assertThat(userViews).isEmpty();
  }

  @Test
  public void findUserById() {
    User user = users.get(0);
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    UserDto userView = userService.findUserById(user.getId());
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

    UserDto userView = userService.findUserByLoginId(user.getLoginId());
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

    UserDto userView = userService.createUser(userTransformer.transform(user));
    assertThat(userView).isNotNull();
    compareUser(userView, user);
  }

  @Test
  public void updateUser() {
    String newName = "anyName";
    User user = users.get(0);
    user.setFirstName(newName);
    when(userRepository.save(user)).thenReturn(user);

    UserDto userView = userService.updateUser(userTransformer.transform(user));
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

  private void compareUsers(List<UserDto> userViews, List<User> users) {
    for (int i = 0; i < userViews.size(); i++) {
      UserDto uv = userViews.get(i);
      User u = users.get(i);
      compareUser(uv, u);
    }
  }

  private void compareUser(UserDto uv, User u) {
    assertThat(uv.getId()).isEqualTo(u.getId());
    assertThat(uv.getLoginId()).isEqualTo(u.getLoginId());
    assertThat(uv.getFirstName()).isEqualTo(u.getFirstName());
    assertThat(uv.getLastName()).isEqualTo(u.getLastName());
    assertThat(uv.getEmailId()).isEqualTo(u.getEmailId());
    assertThat(uv.isActive()).isEqualTo(u.isActive());

    RoleDto rv = uv.getRoleDto();
    Role r = u.getRole();
    compareRoleState(rv, r);
    
  }

  private void compareRoleState(RoleDto rv, Role r) {
    assertThat(rv.getId()).isEqualTo(r.getId());
    assertThat(rv.getName()).isEqualTo(r.getName());
    assertThat(rv.getDescription()).isEqualTo(r.getDescription());
    assertThat(rv.isActive()).isEqualTo(r.isActive());
  }
}

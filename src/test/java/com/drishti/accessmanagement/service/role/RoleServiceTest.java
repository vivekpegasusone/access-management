package com.drishti.accessmanagement.service.role;

import com.drishti.accessmanagement.dao.role.RoleRepository;
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

import static com.drishti.accessmanagement.utility.RoleFixture.anyRole;
import static com.drishti.accessmanagement.utils.RoleUtility.prepareRoleViewFromRole;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

  private List<Role> roles;

  @Mock
  private RoleRepository roleRepository;

  @InjectMocks
  private RoleService roleService = new RoleServiceImpl();

  @BeforeEach
  public void setup() {
    roles = new ArrayList<>();
    roles.add(anyRole());
    roles.add(anyRole());
  }

  @Test
  public void getRoles() {
    when(roleRepository.findByActiveTrue()).thenReturn(roles);

    List<RoleView> roleViews = roleService.getRoles();
    assertThat(roleViews).isNotEmpty();
    assertThat(roleViews).hasSize(2);
    compareRoles(roleViews, roles);

    roles.clear();
    roleViews = roleService.getRoles();
    assertThat(roleViews).isEmpty();
    assertThat(roleViews).hasSize(0);
  }

  @Test
  public void getRolesWhenNoRoleExists() {
    when(roleRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

    List<RoleView> roleViews = roleService.getRoles();
    assertThat(roleViews).isEmpty();
  }

  @Test
  public void findRoleById() {
    Role role = roles.get(0);
    when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

    RoleView roleView = roleService.findRoleById(role.getId());
    assertThat(roleView).isNotNull();
    compareRole(roleView, role);
  }

  @Test
  public void findRoleByInvalidId() {
    when(roleRepository.findById(1L)).thenReturn(Optional.empty());
    RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
      roleService.findRoleById(1L)
    );

    assertEquals("No record exist for given role id 1", exception.getMessage());
  }

  @Test
  public void createRole() {
    Role role = roles.get(0);
    when(roleRepository.save(role)).thenReturn(role);

    RoleView roleView = roleService.createRole(prepareRoleViewFromRole(role));
    assertThat(roleView).isNotNull();
    compareRole(roleView, role);
  }

  @Test
  public void updateRole() {
    String newName = "anyName";
    Role role = roles.get(0);
    role.setName(newName);
    when(roleRepository.save(role)).thenReturn(role);

    RoleView roleView = roleService.updateRole(prepareRoleViewFromRole(role));
    assertThat(roleView).isNotNull();
    assertThat(roleView.getName()).isEqualTo(newName);
    compareRole(roleView, role);
  }

  @Test
  public void deleteRoleById() {
    Role role = roles.get(0);
    roleService.deleteRoleById(role.getId());
    verify(roleRepository, times(1)).deleteById(role.getId());
  }

  private void compareRoles(List<RoleView> roleViews, List<Role> roles) {
    for (int i = 0; i < roleViews.size(); i++) {
      RoleView rv = roleViews.get(i);
      Role r = roles.get(i);
      compareRole(rv, r);
    }
  }

  private void compareRole(RoleView rv, Role r) {
    assertThat(rv.getId()).isEqualTo(r.getId());
    assertThat(rv.getName()).isEqualTo(r.getName());
    assertThat(rv.getDescription()).isEqualTo(r.getDescription());
    assertThat(rv.isActive()).isEqualTo(r.isActive());

    for (int i = 0; i < rv.getUserViews().size(); i++) {
      UserView uv = rv.getUserViews().get(i);
      User u = r.getUsers().get(i);
      compareUserState(uv, u);
    }
  }

  private void compareUserState(UserView uv, User u) {
    assertThat(uv.getId()).isEqualTo(u.getId());
    assertThat(uv.getFirstName()).isEqualTo(u.getFirstName());
    assertThat(uv.getLastName()).isEqualTo(u.getLastName());
    assertThat(uv.getLoginId()).isEqualTo(u.getLoginId());
    assertThat(uv.getEmailId()).isEqualTo(u.getEmailId());
    assertThat(uv.isActive()).isEqualTo(u.isActive());
  }
}
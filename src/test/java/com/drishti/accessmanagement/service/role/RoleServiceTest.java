package com.drishti.accessmanagement.service.role;

import static com.drishti.accessmanagement.utility.RoleFixture.anyRole;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.dto.user.UserDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.role.RoleRepository;
import com.drishti.accessmanagement.repository.entity.role.Role;
import com.drishti.accessmanagement.repository.entity.user.User;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.role.RoleTransformer;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

  private List<Role> roles;

  @Mock
  private RoleRepository roleRepository;

  @InjectMocks
  private RoleService roleService = new RoleServiceImpl();
  
  private Transformer<Role, RoleDto> roleTransformer = new RoleTransformer();

  @BeforeEach
  public void setup() {
    roles = new ArrayList<>();
    roles.add(anyRole());
    roles.add(anyRole());
  }

  @Test
  public void getRoles() {
    when(roleRepository.findByActiveTrue()).thenReturn(roles);

    List<RoleDto> roleViews = roleService.findActiveRoles();
    assertThat(roleViews).isNotEmpty();
    assertThat(roleViews).hasSize(2);
    compareRoles(roleViews, roles);

    roles.clear();
    roleViews = roleService.findActiveRoles();
    assertThat(roleViews).isEmpty();
    assertThat(roleViews).hasSize(0);
  }

  @Test
  public void getRolesWhenNoRoleExists() {
    when(roleRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

    List<RoleDto> roleViews = roleService.findActiveRoles();
    assertThat(roleViews).isEmpty();
  }

  @Test
  public void findRoleById() {
    Role role = roles.get(0);
    when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

    RoleDto roleView = roleService.findRoleById(role.getId());
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

    RoleDto roleView = roleService.createRole(roleTransformer.transform(role));
    assertThat(roleView).isNotNull();
    compareRole(roleView, role);
  }

  @Test
  public void updateRole() {
    String newName = "anyName";
    Role role = roles.get(0);
    role.setName(newName);
    when(roleRepository.save(role)).thenReturn(role);

    RoleDto roleView = roleService.updateRole(roleTransformer.transform(role));
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

  private void compareRoles(List<RoleDto> roleViews, List<Role> roles) {
    for (int i = 0; i < roleViews.size(); i++) {
      RoleDto rv = roleViews.get(i);
      Role r = roles.get(i);
      compareRole(rv, r);
    }
  }

  private void compareRole(RoleDto rd, Role r) {
    assertThat(rd.getId()).isEqualTo(r.getId());
    assertThat(rd.getName()).isEqualTo(r.getName());
    assertThat(rd.getDescription()).isEqualTo(r.getDescription());
    assertThat(rd.isActive()).isEqualTo(r.isActive());

    for (int i = 0; i < rd.getUserDtos().size(); i++) {
      UserDto uv = rd.getUserDtos().get(i);
      User u = r.getUsers().get(i);
      compareUserState(uv, u);
    }
  }

  private void compareUserState(UserDto ud, User u) {
    assertThat(ud.getId()).isEqualTo(u.getId());
    assertThat(ud.getFirstName()).isEqualTo(u.getFirstName());
    assertThat(ud.getLastName()).isEqualTo(u.getLastName());
    assertThat(ud.getLoginId()).isEqualTo(u.getLoginId());
    assertThat(ud.getEmailId()).isEqualTo(u.getEmailId());
    assertThat(ud.isActive()).isEqualTo(u.isActive());
  }
}
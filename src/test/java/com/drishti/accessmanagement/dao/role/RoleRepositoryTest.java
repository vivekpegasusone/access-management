package com.drishti.accessmanagement.dao.role;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.entity.role.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {ApplicationTestConfiguration.class})
public class RoleRepositoryTest {

  private static String name;
  private static String description;

  @Autowired
  private RoleRepository roleRepository;

  @BeforeAll
  public static void beforeAllTests(){
    name = "TestName";
    description = "TestDescription";
  }

  @Test
  public void testFindByName(){
    Role role = new Role.RoleBuilder(name).setDescription(description).setActive(true).build();

    role = roleRepository.save(role);

    Optional<Role> optionalRole = roleRepository.findByName(role.getName());

    assertThat(optionalRole.isPresent()).isTrue();

    if(optionalRole.isPresent()) {
      Role savedRole = optionalRole.get();
      assertThat(name).isEqualTo(savedRole.getName());
      assertThat(description).isEqualTo(savedRole.getDescription());
      assertThat(savedRole.isActive()).isTrue();

      assertThat(savedRole.getAudit().getCreatedOn()).isNotNull();
      assertThat(savedRole.getAudit().getCreatedBy()).isNotNull();
      assertThat(savedRole.getAudit().getUpdatedOn()).isNotNull();
      assertThat(savedRole.getAudit().getUpdatedBy()).isNotNull();
    }
  }

  @Test
  public void testFindByActiveTrue(){
    Role role = new Role.RoleBuilder(name).setDescription(description).setActive(true).build();

    roleRepository.save(role);
    List<Role> roles = roleRepository.findByActiveTrue();

    assertThat(roles).isNotEmpty();
    assertThat(roles.size()).isEqualTo(1);
  }

  @Test
  public void testFindByActiveFalse(){
    Role role = new Role.RoleBuilder(name).setDescription(description).setActive(false).build();

    roleRepository.save(role);
    List<Role> roles = roleRepository.findByActiveFalse();

    assertThat(roles).isNotEmpty();
    assertThat(roles.size()).isEqualTo(1);
  }
}
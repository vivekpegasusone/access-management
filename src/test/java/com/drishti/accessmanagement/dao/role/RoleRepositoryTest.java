package com.drishti.accessmanagement.dao.role;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.repository.dao.application.ApplicationRepository;
import com.drishti.accessmanagement.repository.dao.role.RoleRepository;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.role.Role;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

  private Application application;
  
  @Autowired
  private RoleRepository roleRepository;
  
  @Autowired
  private ApplicationRepository applicationRepository;

  @BeforeAll
  public static void beforeAllTests(){
    name = "TestName";
    description = "TestDescription";
  }

  @BeforeEach
  public void beforeEachTest() {
    application = new Application.ApplicationBuilder("TestApp").setActive(true).build();
    applicationRepository.save(application);
  }
  
  @AfterEach
  public void afterEachTest() {
    applicationRepository.delete(application);
  }
  
  @Test
  public void testFindByName(){
    Role role = new Role.RoleBuilder(name).setDescription(description).setActive(true)
        .setApplication(application).build();

    role = roleRepository.save(role);

    Optional<Role> optionalRole = roleRepository.findByName(role.getName());

    assertThat(optionalRole.isPresent()).isTrue();

    if(optionalRole.isPresent()) {
      Role savedRole = optionalRole.get();
      assertThat(name).isEqualTo(savedRole.getName());
      assertThat(description).isEqualTo(savedRole.getDescription());
      assertThat(savedRole.isActive()).isTrue();

      assertThat(savedRole.getCreatedOn()).isNotNull();
      assertThat(savedRole.getCreatedBy()).isNotNull();
      assertThat(savedRole.getUpdatedOn()).isNotNull();
      assertThat(savedRole.getUpdatedBy()).isNotNull();
    }
  }

  @Test
  public void testFindByActiveTrue(){
    Role role = new Role.RoleBuilder(name).setDescription(description).setActive(true)
        .setApplication(application).build();

    roleRepository.save(role);
    List<Role> roles = roleRepository.findByActiveTrue();

    assertThat(roles).isNotEmpty();
    assertThat(roles.size()).isEqualTo(1);
  }

  @Test
  public void testFindByActiveFalse(){
    Role role = new Role.RoleBuilder(name).setDescription(description).setActive(false)
        .setApplication(application).build();

    roleRepository.save(role);
    List<Role> roles = roleRepository.findByActiveFalse();

    assertThat(roles).isNotEmpty();
    assertThat(roles.size()).isEqualTo(1);
  }
}
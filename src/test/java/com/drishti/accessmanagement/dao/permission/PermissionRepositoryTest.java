package com.drishti.accessmanagement.dao.permission;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.dao.action.ActionRepository;
import com.drishti.accessmanagement.dao.resource.ResourceRepository;
import com.drishti.accessmanagement.entity.action.Action;
import com.drishti.accessmanagement.entity.permission.Permission;
import com.drishti.accessmanagement.entity.resource.Resource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {ApplicationTestConfiguration.class})
public class PermissionRepositoryTest {

  @Autowired
  private ActionRepository actionRepository;

  @Autowired
  private ResourceRepository resourceRepository;

  @Autowired
  private PermissionRepository permissionRepository;

  private static String name;
  private static Action action;
  private static Resource resource;

  @BeforeAll
  public static void beforeAllTests(){
    name = "TestName";
    action = new Action.ActionBuilder("TestAction").setDescription("TestDescription").setActive(true).build();
    resource = new Resource.ResourceBuilder("TestResource").setActive(true).build();
  }

  @Test
  public void testFindByName() {
    Permission permission = new Permission.PermissionBuilder(name).setAction(action).setResource(resource).build();

    actionRepository.save(action);
    resourceRepository.save(resource);
    permission = permissionRepository.save(permission);

    Optional<Permission> optionalPermission = permissionRepository.findByName(permission.getName());

    assertThat(optionalPermission.isPresent()).isTrue();

    if(optionalPermission.isPresent()) {
      Permission savedPermission = optionalPermission.get();
      assertThat(name).isEqualTo(savedPermission.getName());

      assertThat(savedPermission.getAudit().getCreatedOn()).isNotNull();
      assertThat(savedPermission.getAudit().getCreatedBy()).isNotNull();
      assertThat(savedPermission.getAudit().getUpdatedOn()).isNotNull();
      assertThat(savedPermission.getAudit().getUpdatedBy()).isNotNull();
    }
  }
}
package com.drishti.accessmanagement.dao.permission;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.repository.dao.action.ActionRepository;
import com.drishti.accessmanagement.repository.dao.application.ApplicationRepository;
import com.drishti.accessmanagement.repository.dao.permission.PermissionRepository;
import com.drishti.accessmanagement.repository.dao.resource.ResourceRepository;
import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.permission.Permission;
import com.drishti.accessmanagement.repository.entity.resource.Resource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.awt.desktop.AppReopenedListener;
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
  
  @Autowired
  private ApplicationRepository applicationRepository;

  private static String name;
  private static Action action;
  private static Resource resource;
  private static Application application;

  @BeforeAll
  public static void beforeAllTests(){
    name = "TestName";
    
    application = new Application.ApplicationBuilder("TestApp").setId(1L).setActive(true).build();
    
    action = new Action.ActionBuilder("TestAction").setDescription("TestDescription").setActive(true)
        .setApplication(application).build();
    
    resource = new Resource.ResourceBuilder("TestResource").setActive(true).setApplication(application)
        .build();    
  }

  @Test
  public void testFindByName() {
    Permission permission = new Permission.PermissionBuilder(name).setAction(action).setResource(resource).build();

    applicationRepository.save(application);
    actionRepository.save(action);
    resourceRepository.save(resource);
    permission = permissionRepository.save(permission);

    Optional<Permission> optionalPermission = permissionRepository.findByName(permission.getName());

    assertThat(optionalPermission.isPresent()).isTrue();

    if(optionalPermission.isPresent()) {
      Permission savedPermission = optionalPermission.get();
      assertThat(name).isEqualTo(savedPermission.getName());

      assertThat(savedPermission.getCreatedOn()).isNotNull();
      assertThat(savedPermission.getCreatedBy()).isNotNull();
      assertThat(savedPermission.getUpdatedOn()).isNotNull();
      assertThat(savedPermission.getUpdatedBy()).isNotNull();
    }
  }
}
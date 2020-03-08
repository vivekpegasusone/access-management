package com.drishti.accessmanagement.dao.action;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.repository.dao.action.ActionRepository;
import com.drishti.accessmanagement.repository.dao.application.ApplicationRepository;
import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.application.Application;

@DataJpaTest
@ContextConfiguration(classes = {ApplicationTestConfiguration.class})
public class ActionRepositoryTest {

  private static String name;
  private static String description;
  
  private Application application;
  
  @Autowired
  private ActionRepository actionRepository;
  
  @Autowired
  private ApplicationRepository applicationRepository;

  @BeforeAll
  public static void beforeAllTests() {
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
  public void testFindByName() {
    Action action = new Action.ActionBuilder(name).setActive(true).setApplication(application).build();
    action = actionRepository.save(action);

    Optional<Action> optionalResource = actionRepository.findByName(action.getName());

    assertThat(optionalResource.isPresent()).isTrue();

    if(optionalResource.isPresent()) {
      Action savedAction = optionalResource.get();
      assertThat(name).isEqualTo(savedAction.getName());
      assertThat(savedAction.isActive()).isTrue();

      assertThat(savedAction.getCreatedOn()).isNotNull();
      assertThat(savedAction.getCreatedBy()).isNotNull();
      assertThat(savedAction.getUpdatedOn()).isNotNull();
      assertThat(savedAction.getUpdatedBy()).isNotNull();
    }
  }

  @Test
  public void testFindByActiveTrue() {
    Action action = new Action.ActionBuilder(name).setActive(true).setApplication(application).build();
    actionRepository.save(action);
    
    List<Action> actions = actionRepository.findByActiveTrue();

    assertThat(actions).isNotEmpty();
    assertThat(actions.size()).isEqualTo(1);
  }

  @Test
  public void testFindByActiveFalse() {
    Action action = new Action.ActionBuilder(name).setActive(false).setApplication(application).build();
    actionRepository.save(action);
    
    List<Action> actions = actionRepository.findByActiveFalse();

    assertThat(actions).isNotEmpty();
    assertThat(actions.size()).isEqualTo(1);
  }
}
package com.drishti.accessmanagement.dao.action;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.entity.action.Action;
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
public class ActionRepositoryTest {

  @Autowired
  private ActionRepository actionRepository;

  private static String name;
  private static String description;

  @BeforeAll
  public static void beforeAllTests(){
    name = "TestName";
    description = "TestDescription";
  }

  @Test
  public void testFindByName() {
    Action action = new Action.ActionBuilder(name).setActive(true).build();
    action = actionRepository.save(action);

    Optional<Action> optionalResource = actionRepository.findByName(action.getName());

    assertThat(optionalResource.isPresent()).isTrue();

    if(optionalResource.isPresent()) {
      Action savedAction = optionalResource.get();
      assertThat(name).isEqualTo(savedAction.getName());
      assertThat(savedAction.isActive()).isTrue();

      assertThat(savedAction.getAudit().getCreatedOn()).isNotNull();
      assertThat(savedAction.getAudit().getCreatedBy()).isNotNull();
      assertThat(savedAction.getAudit().getUpdatedOn()).isNotNull();
      assertThat(savedAction.getAudit().getUpdatedBy()).isNotNull();
    }
  }

  @Test
  public void testFindByActiveTrue() {
    Action action = new Action.ActionBuilder(name).setActive(true).build();

    actionRepository.save(action);
    List<Action> actions = actionRepository.findByActiveTrue();

    assertThat(actions).isNotEmpty();
    assertThat(actions.size()).isEqualTo(1);
  }

  @Test
  public void testFindByActiveFalse() {
    Action action = new Action.ActionBuilder(name).setActive(false).build();

    actionRepository.save(action);
    List<Action> actions = actionRepository.findByActiveFalse();

    assertThat(actions).isNotEmpty();
    assertThat(actions.size()).isEqualTo(1);
  }
}
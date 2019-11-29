package com.drishti.accessmanagement.dao.user;

import com.drishti.accessmanagement.entity.audit.embedded.Audit;
import com.drishti.accessmanagement.entity.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  void injectedComponentsAreNotNull(){
    assertThat(testEntityManager).isNotNull();
    assertThat(userRepository).isNotNull();

    User user = new User();
    user.setLoginId("singhvh");
    user.setFirstName("Vivek");
    user.setLastName("Singh");
    user.setEnabled(true);
    user.setPassword("gfasjf");

    User persistedUser = testEntityManager.persist(user);

    assertThat(persistedUser.getAudit().getCreatedOn()).isNotNull();
  }
}
package com.drishti.accessmanagement.dao.user;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.entity.audit.embedded.Audit;
import com.drishti.accessmanagement.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {ApplicationTestConfiguration.class})
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void injectedComponentsAreNotNull(){
    assertThat(userRepository).isNotNull();

    User user = new User();
    user.setLoginId("singhvh");
    user.setFirstName("Vivek");
    user.setLastName("Singh");
    user.setEnabled(true);
    user.setPassword("gfasjf");

    userRepository.save(user);
    
    User persistedUser = userRepository.findByLoginId("singhvh");

    System.out.println(persistedUser.getAudit().getCreatedOn());
    System.out.println(persistedUser.getAudit().getCreatedBy());
    System.out.println(persistedUser.getAudit().getUpdatedOn());
    System.out.println(persistedUser.getAudit().getUpdatedBy());
    
    System.out.println(persistedUser);
    
    assertThat(persistedUser.getAudit().getCreatedOn()).isNotNull();
  }
}

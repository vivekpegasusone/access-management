package com.drishti.accessmanagement.dao.user;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.entity.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@DataJpaTest
@ContextConfiguration(classes = {ApplicationTestConfiguration.class})
public class UserRepositoryTest {

  private static String loginId;
  private static String firstName;
  private static String lastName;
  private static String password;
  private static String emailId;

  @Autowired
  private UserRepository userRepository;

  @BeforeAll
  public static void beforeAllTests(){
    loginId = anyString();
    firstName = anyString();
    lastName = anyString();
    password = anyString();
    emailId = "test@abc.com";
  }

  @Test
  void testPersistAndRetrieveUser(){
    User user = new User(loginId, firstName, lastName, emailId, true);
    user.setPassword(password);
    user = userRepository.save(user);

    Optional<User> optionalUser = userRepository.findByLoginId(user.getLoginId());

    assertThat(optionalUser.isPresent()).isTrue();

    if(optionalUser.isPresent()) {
      User savedUser = optionalUser.get();
      assertThat(loginId).isEqualTo(savedUser.getLoginId());
      assertThat(firstName).isEqualTo(savedUser.getFirstName());
      assertThat(lastName).isEqualTo(savedUser.getLastName());
      assertThat(emailId).isEqualTo(savedUser.getEmailId());
      assertThat(password).isEqualTo(savedUser.getPassword());
      assertThat(savedUser.isActive()).isTrue();

      assertThat(savedUser.getAudit().getCreatedOn()).isNotNull();
      assertThat(savedUser.getAudit().getCreatedBy()).isNotNull();
      assertThat(savedUser.getAudit().getUpdatedOn()).isNotNull();
      assertThat(savedUser.getAudit().getUpdatedBy()).isNotNull();
    }
  }
}

package com.drishti.accessmanagement.dao.user;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.entity.user.User;
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
    loginId = "TestLoginId";
    firstName = "TestFirstName";
    lastName = "TestLastName";
    password = "TestPassword";
    emailId = "test@abc.com";
  }

  @Test
  public void testFindByLoginId(){
    User user = new User.UserBuilder(loginId).setFirstName(firstName).setLastName(lastName)
        .setEmailId(emailId).setActive(true).setPassword(password).build();

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

  @Test
  public void testFindByActiveTrue(){
    User user = new User.UserBuilder(loginId).setFirstName(firstName).setLastName(lastName)
        .setEmailId(emailId).setActive(true).setPassword(password).build();

    userRepository.save(user);
    List<User> users = userRepository.findByActiveTrue();

    assertThat(users).isNotEmpty();
    assertThat(users.size()).isEqualTo(1);
  }

  @Test
  public void testFindByActiveFalse(){
    User user = new User.UserBuilder(loginId).setFirstName(firstName).setLastName(lastName)
        .setEmailId(emailId).setActive(false).setPassword(password).build();

    userRepository.save(user);
    List<User> users = userRepository.findByActiveFalse();

    assertThat(users).isNotEmpty();
    assertThat(users.size()).isEqualTo(1);
  }
}

package com.drishti.accessmanagement.dao.resource;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.repository.dao.application.ApplicationRepository;
import com.drishti.accessmanagement.repository.dao.resource.ResourceRepository;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.resource.Resource;

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
public class ResourceRepositoryTest {

  private static String name;
  
  private static String description;
  
  private Application application; 
  
  @Autowired
  private ResourceRepository resourceRepository;
  
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
  public void testFindByName() {
    Resource resource = new Resource.ResourceBuilder(name).setDescription(description).setActive(true).setApplication(application).build();
    resource = resourceRepository.save(resource);

    Optional<Resource> optionalResource = resourceRepository.findByName(resource.getName());

    assertThat(optionalResource.isPresent()).isTrue();

    if(optionalResource.isPresent()) {
      Resource savedResource = optionalResource.get();
      assertThat(name).isEqualTo(savedResource.getName());
      assertThat(description).isEqualTo(savedResource.getDescription());
      assertThat(savedResource.isActive()).isTrue();

      assertThat(savedResource.getCreatedOn()).isNotNull();
      assertThat(savedResource.getCreatedBy()).isNotNull();
      assertThat(savedResource.getUpdatedOn()).isNotNull();
      assertThat(savedResource.getUpdatedBy()).isNotNull();
    }
  }

  @Test
  public void testFindByActiveTrue() {
    Resource resource = new Resource.ResourceBuilder(name).setDescription(description).setActive(true).setApplication(application).build();

    resourceRepository.save(resource);
    List<Resource> resources = resourceRepository.findByActiveTrue();

    assertThat(resources).isNotEmpty();
    assertThat(resources.size()).isEqualTo(1);
  }

  @Test
  public void testFindByActiveFalse() {
    Resource resource = new Resource.ResourceBuilder(name).setDescription(description).setActive(false).setApplication(application).build();

    resourceRepository.save(resource);
    List<Resource> resources = resourceRepository.findByActiveFalse();

    assertThat(resources).isNotEmpty();
    assertThat(resources.size()).isEqualTo(1);
  }
}
package com.drishti.accessmanagement.dao.resource;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;
import com.drishti.accessmanagement.entity.resource.Resource;
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
public class ResourceRepositoryTest {

  @Autowired
  private ResourceRepository resourceRepository;

  private static String name;

  @BeforeAll
  public static void beforeAllTests(){
    name = "TestName";
  }

  @Test
  public void testFindByName() {
    Resource resource = new Resource.ResourceBuilder(name).setActive(true).build();
    resource = resourceRepository.save(resource);

    Optional<Resource> optionalResource = resourceRepository.findByName(resource.getName());

    assertThat(optionalResource.isPresent()).isTrue();

    if(optionalResource.isPresent()) {
      Resource savedResource = optionalResource.get();
      assertThat(name).isEqualTo(savedResource.getName());
      assertThat(savedResource.isActive()).isTrue();

      assertThat(savedResource.getAudit().getCreatedOn()).isNotNull();
      assertThat(savedResource.getAudit().getCreatedBy()).isNotNull();
      assertThat(savedResource.getAudit().getUpdatedOn()).isNotNull();
      assertThat(savedResource.getAudit().getUpdatedBy()).isNotNull();
    }
  }

  @Test
  public void testFindByActiveTrue() {
    Resource resource = new Resource.ResourceBuilder(name).setActive(true).build();

    resourceRepository.save(resource);
    List<Resource> resources = resourceRepository.findByActiveTrue();

    assertThat(resources).isNotEmpty();
    assertThat(resources.size()).isEqualTo(1);
  }

  @Test
  public void testFindByActiveFalse() {
    Resource resource = new Resource.ResourceBuilder(name).setActive(false).build();

    resourceRepository.save(resource);
    List<Resource> resources = resourceRepository.findByActiveFalse();

    assertThat(resources).isNotEmpty();
    assertThat(resources.size()).isEqualTo(1);
  }
}
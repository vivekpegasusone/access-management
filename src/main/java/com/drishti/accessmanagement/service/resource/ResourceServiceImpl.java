package com.drishti.accessmanagement.service.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.resource.ResourceRepository;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.resource.Resource;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;
import com.drishti.accessmanagement.service.transformer.resource.ResourceTransformer;

@Service
class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceRepository resourceRepository;
  
  private Transformer<Resource, ResourceDto> resourceTransformer = new ResourceTransformer();
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public List<ResourceDto> findAll() {
    List<Resource> resources = resourceRepository.findAll();
    return resourceTransformer.transform(resources);
  }

  @Override
  public List<ResourceDto> findActiveResources() {
    List<Resource> resourceList = resourceRepository.findByActiveTrue();

    if (!resourceList.isEmpty()) {
      return resourceTransformer.transform(resourceList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public List<ResourceDto> findInActiveResources() {
    List<Resource> resourceList = resourceRepository.findByActiveFalse();

    if (!resourceList.isEmpty()) {
      return resourceTransformer.transform(resourceList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public ResourceDto findResourceById(Long id) throws RecordNotFoundException {
    Optional<Resource> optionalResource = resourceRepository.findById(id);

    if (optionalResource.isPresent()) {
      return resourceTransformer.transform(optionalResource.get());
    } else {
      throw new RecordNotFoundException("No record exist for given resource id " + id);
    }
  }

  @Override
  public ResourceDto createResource(ResourceDto resourceDto) {
    Resource resource = resourceTransformer.transform(resourceDto);
    resource = resourceRepository.save(resource);
    return resourceTransformer.transform(resource);
  }

  @Override
  public ResourceDto updateResource(ResourceDto resourceDto) throws RecordNotFoundException {
    Resource resource = resourceRepository.findById(resourceDto.getId()).get();
    resource.setName(resourceDto.getName());
    resource.setDescription(resourceDto.getDescription());
    resource.setApplication(applicationTransformer.transform(resourceDto.getApplicationDto()));
     
    resourceRepository.save(resource);    
    return resourceTransformer.transform(resource);
  }

  @Override
  public void deleteResourceById(Long id) throws RecordNotFoundException {
    Resource resource = resourceRepository.findById(id).get();
    resource.setActive(false);
    // set all permission/mapping to false
    resourceRepository.save(resource); 
  }

  @Override
  public List<ResourceDto> findResourcesByApplicationId(Long applicationId) {
    List<Resource> resources = resourceRepository.findResourcesByApplicationId(applicationId);
    return resourceTransformer.transform(resources);
  }
}

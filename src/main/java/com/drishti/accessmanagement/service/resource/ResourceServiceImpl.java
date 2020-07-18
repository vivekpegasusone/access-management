package com.drishti.accessmanagement.service.resource;

import static com.drishti.accessmanagement.utils.StringUtility.apendTimeInMillis;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.InvalidRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.resource.ResourceRepository;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.resource.Resource;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;
import com.drishti.accessmanagement.service.transformer.resource.ResourceTransformer;

@Service
class ResourceServiceImpl implements ResourceService {

  private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
  
  @Autowired
  private ResourceRepository resourceRepository;
  
  private Transformer<Resource, ResourceDto> resourceTransformer = new ResourceTransformer();
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public List<ResourceDto> findAll() {
    logger.info("Retrieving all resource records.");
    List<Resource> resources = resourceRepository.findAll();
    return resourceTransformer.transform(resources);
  }

  @Override
  public List<ResourceDto> findActiveResources() {
    logger.info("Retrieving all active resource records.");
    List<Resource> resourceList = resourceRepository.findByActiveTrue();

    if (!resourceList.isEmpty()) {
      return resourceTransformer.transform(resourceList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public List<ResourceDto> findInActiveResources() {
    logger.info("Retrieving all inactive resource records.");
    List<Resource> resourceList = resourceRepository.findByActiveFalse();

    if (!resourceList.isEmpty()) {
      return resourceTransformer.transform(resourceList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public ResourceDto findResourceById(Long id) throws RecordNotFoundException {
    logger.info("Retrieving resource for id {}.", id);
    Optional<Resource> optionalResource = resourceRepository.findById(id);

    if (optionalResource.isPresent()) {
      logger.debug("Resource record found for id {}.", id);
      return resourceTransformer.transform(optionalResource.get());
    } else {
      logger.debug("Resource record not found for id {}.", id);
      throw new RecordNotFoundException("No record exist for given resource id " + id);
    }
  }

  @Override
  public ResourceDto createResource(ResourceDto resourceDto) throws DuplicateRecordException {
    if (Objects.nonNull(resourceDto)) {
      logger.info("Creating resource record for resource {}.", resourceDto.getName());
      Optional<Resource> optionalResource = resourceRepository.findByName(resourceDto.getName());
      
      if(optionalResource.isPresent()) {
        throw new DuplicateRecordException("Duplicate record. Resource name already exists.");
      } else {
        Resource resource = resourceTransformer.transform(resourceDto);
        resource = resourceRepository.save(resource);
        return resourceTransformer.transform(resource);
      }       
    } else {
      logger.info("Can not create resource. Resource information not present.");
      throw new InvalidRecordException("Can not create resource. Resource information not present.");
    }
  }

  @Override
  public ResourceDto updateResource(ResourceDto resourceDto) throws RecordNotFoundException, DuplicateRecordException {
    if (Objects.nonNull(resourceDto) && nonNull(resourceDto.getId())) {
      logger.info("Updating resource record for id {}.", resourceDto.getId());
      
      Optional<Resource> optionalResource =  resourceRepository.findById(resourceDto.getId());
      if (optionalResource.isPresent()) {
        logger.debug("Resource record found for id {}.", resourceDto.getId());  
        Resource resource = optionalResource.get();
        
        if (resource.getName().equalsIgnoreCase(resourceDto.getName())) {
          throw new DuplicateRecordException("Duplicate resource name. Resource name already exists.");
        } else {
          resource.setName(resourceDto.getName());
          resource.setDescription(resourceDto.getDescription());
          resource.setApplication(applicationTransformer.transform(resourceDto.getApplicationDto()));

          resourceRepository.save(resource);
          return resourceTransformer.transform(resource);   
        }         
      } else {
        logger.debug("Can not update resource. No record not found for resource id {}.", resourceDto.getId());
        throw new RecordNotFoundException("No record exist for given resource id " + resourceDto.getId());        
      }  
    } else {
      logger.info("Can not update resource. Resource id is empty.");
      throw new InvalidRecordException("Can not update resource. Resource id is empty.");
    }
  }

  @Override
  public void deleteResourceById(Long id) throws RecordNotFoundException {
    logger.info("Archiving resource record for id {}.", id);
    Optional<Resource> optionalResource = resourceRepository.findById(id);

    if (optionalResource.isPresent()) {
      logger.debug("Resource record found for id {}.", id);
      Resource resource = optionalResource.get();
      resource.setActive(false);
      resource.setName(apendTimeInMillis(resource.getName()));
      // set all permission/mapping to false
      resourceRepository.save(resource); 
    } else {
      logger.debug("Can not archive resource. No record found for resource id {}.", id);
      throw new RecordNotFoundException("No record exist for given resource id " + id);
    }    
  }

  @Override
  public List<ResourceDto> findResourcesByApplicationId(Long applicationId) {
    logger.info("Retrieving resources for application id {}.", applicationId);
    List<Resource> resources = resourceRepository.findResourcesByApplicationId(applicationId);
    return resourceTransformer.transform(resources);
  }
}

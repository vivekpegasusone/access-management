package com.drishti.accessmanagement.service.transformer.resource;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.resource.Resource;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;

public class ResourceTransformer implements Transformer<Resource, ResourceDto> {

  private static final Logger logger = LoggerFactory.getLogger(ResourceTransformer.class);
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public ResourceDto transform(Resource resource) {
    logger.info("Transforming resource entity to resource dto.");
    ResourceDto resourceDto = null;
    if (Objects.nonNull(resource)) {
      resourceDto = new ResourceDto(resource.getId(), resource.getName(), resource.getDescription(),
          resource.isActive());
      resourceDto.setCreatedBy(resource.getCreatedBy());
      resourceDto.setCreatedOn(resource.getCreatedOn());
      resourceDto.setUpdatedBy(resource.getUpdatedBy());
      resourceDto.setUpdatedOn(resource.getUpdatedOn());

      ApplicationDto appDto = applicationTransformer.transform(resource.getApplication());
      resourceDto.setApplicationDto(appDto);
    }
    return resourceDto;
  }

  @Override
  public Resource transform(ResourceDto resourceDto) {
    logger.info("Transforming resource dto to resource entity.");
    Resource resource = null;
    if (Objects.nonNull(resourceDto)) {
      Application application = applicationTransformer.transform(resourceDto.getApplicationDto());

      resource = new Resource.ResourceBuilder(resourceDto.getName()).setId(resourceDto.getId())
          .setDescription(resourceDto.getDescription()).setActive(resourceDto.isActive()).setApplication(application)
          .build();
    }

    return resource;
  }

  @Override
  public List<ResourceDto> transform(List<Resource> resources) {
    logger.info("Transforming resource  entitys to resource dtos.");
    List<ResourceDto> resourceDtos;

    if (CollectionUtils.isEmpty(resources)) {
      resourceDtos = Collections.emptyList();
    } else {
      resourceDtos = resources.stream().map(r -> transform(r)).collect(Collectors.toList());
    }

    return resourceDtos;
  }

}

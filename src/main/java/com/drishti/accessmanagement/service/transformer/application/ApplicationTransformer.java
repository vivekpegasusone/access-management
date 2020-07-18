package com.drishti.accessmanagement.service.transformer.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.service.transformer.Transformer;

public class ApplicationTransformer implements Transformer<Application, ApplicationDto> {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationTransformer.class);
  
  @Override
  public ApplicationDto transform(Application application) {
    logger.info("Transforming application entity to application dto.");
    ApplicationDto applicationDto = null;
    if (Objects.nonNull(application)) {
      applicationDto = new ApplicationDto(application.getId(), application.getName(), application.getDescription(),
          application.isActive(), application.getCreatedBy(), application.getUpdatedBy(), application.getCreatedOn(),
          application.getUpdatedOn());
    }
    
    return applicationDto;
  }

  @Override
  public List<ApplicationDto> transform(List<Application> applications) {
    logger.info("Transforming application entitys to application dtos.");
    List<ApplicationDto> applicationDtos;
    
    if(CollectionUtils.isEmpty(applications)) {
      applicationDtos = Collections.emptyList();
    } else {
      applicationDtos = new ArrayList<>(applications.size());
      
      applications.forEach(a -> {
        applicationDtos.add(new ApplicationDto(a.getId(), a.getName(), a.getDescription(), a.isActive(), 
            a.getCreatedBy(), a.getUpdatedBy(), a.getCreatedOn(), a.getUpdatedOn()));
      });
    }
   return applicationDtos;
  }

  @Override
  public Application transform(ApplicationDto applicationDto) {
    logger.info("Transforming application dto to application entity.");
    Application application = null;
    if (Objects.nonNull(applicationDto)) {
      application = new Application.ApplicationBuilder(applicationDto.getName()).setId(applicationDto.getId())
          .setDescription(applicationDto.getDescription()).setActive(applicationDto.isActive()).build();
    }
    return application;
  }

}

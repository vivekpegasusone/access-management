package com.drishti.accessmanagement.service.application;

import static com.drishti.accessmanagement.utils.StringUtility.apendTimeInMillis;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.InvalidRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.application.ApplicationRepository;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;

@Service
@Transactional(readOnly = true)
public class ApplicationServiceImpl implements ApplicationService {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);
  
  @Autowired
  private ApplicationRepository applicationRepository;
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public List<ApplicationDto> findAll() {
    logger.info("Retrieving all application records.");
    List<Application> applications = applicationRepository.findAll();
    return applicationTransformer.transform(applications);
  }

  @Override
  public List<ApplicationDto> findActiveApplications() {
    logger.info("Retrieving all active application records.");
    List<Application> applications = applicationRepository.findByActiveTrue();
    return applicationTransformer.transform(applications);
  }

  @Override
  public List<ApplicationDto> findInActiveApplications() {
    logger.info("Retrieving all inactive application records.");
    List<Application> applications = applicationRepository.findByActiveFalse();
    return applicationTransformer.transform(applications);
  }

  @Override
  public ApplicationDto findApplicationById(Long id) throws RecordNotFoundException {
    logger.info("Retrieving application for id {}.", id);
    Optional<Application> applicationOptional = applicationRepository.findById(id);

    if (applicationOptional.isPresent()) {
      logger.debug("Application record found for id {}.", id);
      return applicationTransformer.transform(applicationOptional.get());
    } else {
      logger.debug("Application record not found for id {}.", id);
      throw new RecordNotFoundException("No record exist for given application id " + id);
    }
  }

  @Override
  public ApplicationDto findApplicationByname(String name) throws RecordNotFoundException {
    logger.info("Retrieving application for name {}.", name);
    Optional<Application> applicationOptional = applicationRepository.findByName(name);

    if (applicationOptional.isPresent()) {
      logger.debug("Application record found for name {}.", name);
      return applicationTransformer.transform(applicationOptional.get());
    } else {
      logger.debug("Application record not found for name {}.", name);
      throw new RecordNotFoundException("No record exist for given application name " + name);
    }   
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ApplicationDto createApplication(ApplicationDto applicationDto) throws DuplicateRecordException {
    if (Objects.nonNull(applicationDto)) {
      Optional<Application> applicationOptional = applicationRepository.findByName(applicationDto.getName());
      
      if(applicationOptional.isPresent()) {
        throw new DuplicateRecordException("Duplicate record. Application name already exists.");
      } else {
        logger.info("Creating application record for application {}.", applicationDto.getName());
        Application application = applicationTransformer.transform(applicationDto);
        application = applicationRepository.save(application);
        return applicationTransformer.transform(application);
      }      
    } else {
      logger.info("Can not create application. Application information not present.");
      throw new InvalidRecordException("Can not create application. Application information not present.");
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ApplicationDto updateApplication(ApplicationDto applicationDto) throws RecordNotFoundException, DuplicateRecordException {
    if (Objects.nonNull(applicationDto) && nonNull(applicationDto.getId())) {
      logger.info("Updating application record for id {}.", applicationDto.getId());      
      Optional<Application> applicationOptional = applicationRepository.findById(applicationDto.getId());
      
      if (applicationOptional.isPresent()) {
        logger.debug("Application record found for id {}.", applicationDto.getId());       
        Application application = applicationOptional.get();
        
        if(application.getName().equalsIgnoreCase(applicationDto.getName())) {
          throw new DuplicateRecordException("Duplicate application name. Application name already exists.");
        } else {
          application.setName(applicationDto.getName());
          application.setDescription(applicationDto.getDescription());
          applicationRepository.saveAndFlush(application);
          return applicationTransformer.transform(application);
        }        
      } else {
        logger.debug("Can not update application. No record found for application id {}.", applicationDto.getId());
        throw new RecordNotFoundException("No record exist for given application id " + applicationDto.getId());
      }
    } else {
      logger.info("Can not update application. Application id is empty.");
      throw new InvalidRecordException("Can not update application. Application id is empty.");
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteApplicationById(Long id) throws RecordNotFoundException {
    logger.info("Archiving application record for id {}.", id);
    Optional<Application> applicationOptional = applicationRepository.findById(id);
    
    if (applicationOptional.isPresent()) {
      Application application = applicationOptional.get();
      logger.debug("Application record found for id {}.", application.getId());      
      application.setActive(false);
      application.setName(apendTimeInMillis(application.getName()));
      // set all users, roles, actions, resources false
      applicationRepository.save(application);          
    } else {
      logger.debug("Can not archive application. No record found for application id {}.", id);
      throw new RecordNotFoundException("No record exist for given application id " + id);
    }       
  }  
}

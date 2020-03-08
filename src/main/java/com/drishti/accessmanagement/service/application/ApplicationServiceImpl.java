package com.drishti.accessmanagement.service.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.application.ApplicationRepository;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;

@Service
@Transactional(readOnly = true)
public class ApplicationServiceImpl implements ApplicationService {

  @Autowired
  private ApplicationRepository applicationRepository;
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public List<ApplicationDto> findAll() {
    List<Application> applications = applicationRepository.findAll();
    return applicationTransformer.transform(applications);
  }

  @Override
  public List<ApplicationDto> findActiveApplications() {
    List<Application> applications = applicationRepository.findByActiveTrue();
    return applicationTransformer.transform(applications);
  }

  @Override
  public List<ApplicationDto> findInActiveApplications() {
    List<Application> applications = applicationRepository.findByActiveFalse();
    return applicationTransformer.transform(applications);
  }

  @Override
  public ApplicationDto findApplicationById(Long id) throws RecordNotFoundException {
    Optional<Application> applicationOptional = applicationRepository.findById(id);

    if (applicationOptional.isPresent()) {
      return applicationTransformer.transform(applicationOptional.get());
    } else {
      throw new RecordNotFoundException("No record exist for given application id " + id);
    }
  }

  @Override
  public ApplicationDto findApplicationByname(String name) throws RecordNotFoundException {
    Optional<Application> applicationOptional = applicationRepository.findByName(name);

    if (applicationOptional.isPresent()) {
      return applicationTransformer.transform(applicationOptional.get());
    } else {
      throw new RecordNotFoundException("No record exist for given application name " + name);
    }   
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ApplicationDto createApplication(ApplicationDto applicationDto) {
    Application application = applicationTransformer.transform(applicationDto);
    application = applicationRepository.save(application);
    return applicationTransformer.transform(application);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ApplicationDto updateApplication(ApplicationDto applicationDto) throws RecordNotFoundException {
    Application application = applicationRepository.findById(applicationDto.getId()).get();
    application.setName(applicationDto.getName());
    application.setDescription(applicationDto.getDescription());
    applicationRepository.saveAndFlush(application);
    return applicationTransformer.transform(application);    
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteApplicationById(Long id) throws RecordNotFoundException {
    Application application = applicationRepository.findById(id).get();
    application.setActive(false);
    // set all users, roles, actions, resources false
    applicationRepository.save(application);    
  }  
}

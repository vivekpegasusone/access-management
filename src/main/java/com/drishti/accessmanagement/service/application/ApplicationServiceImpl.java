package com.drishti.accessmanagement.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.dao.application.ApplicationRepository;
import com.drishti.accessmanagement.dto.application.ApplicationView;
import com.drishti.accessmanagement.entity.application.Application;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  @Autowired
  private ApplicationRepository applicationRepository;
  
  @Override
  public List<ApplicationView> findAll() {
    List<Application> applications = applicationRepository.findAll();
    return null;
  }

  @Override
  public List<ApplicationView> findActiveApplications() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ApplicationView> findInActiveApplications() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ApplicationView findApplicationById(Long id) throws RecordNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ApplicationView findApplicationByname(String name) throws RecordNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ApplicationView createApplication(ApplicationView applicationView) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ApplicationView updateApplication(ApplicationView applicationView) throws RecordNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteApplicationById(Long id) throws RecordNotFoundException {
    // TODO Auto-generated method stub

  }

}

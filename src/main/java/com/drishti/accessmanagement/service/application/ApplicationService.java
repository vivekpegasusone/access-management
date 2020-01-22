package com.drishti.accessmanagement.service.application;

import java.util.List;

import com.drishti.accessmanagement.dto.application.ApplicationView;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

public interface ApplicationService {
  
  List<ApplicationView> findAll();

  List<ApplicationView> findActiveApplications();
  
  List<ApplicationView> findInActiveApplications();  

  ApplicationView findApplicationById(Long id) throws RecordNotFoundException;

  ApplicationView findApplicationByname(String name) throws RecordNotFoundException;

  ApplicationView createApplication(ApplicationView applicationView);

  ApplicationView updateApplication(ApplicationView applicationView) throws RecordNotFoundException;

  void deleteApplicationById(Long id) throws RecordNotFoundException;
}

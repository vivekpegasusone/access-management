package com.drishti.accessmanagement.service.application;

import java.util.List;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

public interface ApplicationService {
  
  List<ApplicationDto> findAll();

  List<ApplicationDto> findActiveApplications();
  
  List<ApplicationDto> findInActiveApplications();  

  ApplicationDto findApplicationById(Long id) throws RecordNotFoundException;

  ApplicationDto findApplicationByname(String name) throws RecordNotFoundException;

  ApplicationDto createApplication(ApplicationDto applicationDto) throws DuplicateRecordException;

  ApplicationDto updateApplication(ApplicationDto applicationDto) throws RecordNotFoundException, DuplicateRecordException ;

  void deleteApplicationById(Long id) throws RecordNotFoundException;
}

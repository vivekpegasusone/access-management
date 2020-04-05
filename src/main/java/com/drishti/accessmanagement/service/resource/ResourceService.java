package com.drishti.accessmanagement.service.resource;

import java.util.List;

import com.drishti.accessmanagement.dto.resource.ResourceDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

public interface ResourceService {
  
  List<ResourceDto> findAll();
  
  List<ResourceDto> findActiveResources();
  
  List<ResourceDto> findInActiveResources();  

  ResourceDto findResourceById(Long id) throws RecordNotFoundException;

  ResourceDto createResource(ResourceDto resourceDto);

  ResourceDto updateResource(ResourceDto resourceDto) throws RecordNotFoundException;

  void deleteResourceById(Long id) throws RecordNotFoundException;

  List<ResourceDto> findResourcesByApplicationId(Long applicationId);
}

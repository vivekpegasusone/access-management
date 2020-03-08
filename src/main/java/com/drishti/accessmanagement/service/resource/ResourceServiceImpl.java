package com.drishti.accessmanagement.service.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.repository.dao.resource.ResourceRepository;

@Service
class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceRepository userRepository;
}

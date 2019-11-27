package com.drishti.accessmanagement.service.resource;

import com.drishti.accessmanagement.dao.resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceRepository userRepository;
}

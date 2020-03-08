package com.drishti.accessmanagement.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.repository.dao.action.ActionRepository;

@Service
class ActionServiceImpl implements ActionService {

  @Autowired
  private ActionRepository userRepository;
}

package com.drishti.accessmanagement.service.action;

import com.drishti.accessmanagement.dao.action.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ActionServiceImpl implements ActionService {

  @Autowired
  private ActionRepository userRepository;
}

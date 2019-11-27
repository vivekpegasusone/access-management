package com.drishti.accessmanagement.service.user;

import com.drishti.accessmanagement.dao.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

}

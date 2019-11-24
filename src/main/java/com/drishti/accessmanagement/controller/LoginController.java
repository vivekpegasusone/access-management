package com.drishti.accessmanagement.controller;

import com.drishti.accessmanagement.dto.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  private static final String VIEW_NAME = "login";

  @GetMapping(value = "/login")
  public ModelAndView showLogin() {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);

    UserLogin userLogin = new UserLogin();
    modelAndView.addObject("login", userLogin);

    return modelAndView;
  }
}

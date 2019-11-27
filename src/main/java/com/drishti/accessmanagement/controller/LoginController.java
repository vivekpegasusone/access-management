package com.drishti.accessmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  private static final String VIEW_NAME = "login";

  @GetMapping(value = "/login")
  public String showLogin() {
    return VIEW_NAME;
  }
}

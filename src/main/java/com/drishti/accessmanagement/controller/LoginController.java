package com.drishti.accessmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  private static final String VIEW_NAME = "login";

  @GetMapping(value = {"/", "/login"})
  public String showLogin() {
    return VIEW_NAME;
  }
}

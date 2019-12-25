package com.drishti.accessmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping(value = { "/admin" })
  public String showAdminHome() {
    return "adminHome";
  }

  @GetMapping(value = { "/user" })
  public String showUserHome() {
    return "userHome";
  }
}

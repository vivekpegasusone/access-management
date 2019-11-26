package com.drishti.accessmanagement.controller;

import com.drishti.accessmanagement.dto.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
  
  @GetMapping(value = {"/admin"})
  public String showAdminHome() {
      return "adminHome";
  }
  
  @GetMapping(value = {"/user"})
  public String showUserHome() {
      return "userHome";
  } 
}

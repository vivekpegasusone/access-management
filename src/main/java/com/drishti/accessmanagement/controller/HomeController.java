package com.drishti.accessmanagement.controller;

import com.drishti.accessmanagement.dto.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

  @PostMapping(value = "/home")
  public String showHome(@ModelAttribute UserLogin userLogin) {

    if(userLogin.getUserId().equalsIgnoreCase("Vivek") &&
        userLogin.getPassword().equalsIgnoreCase("123")) {
      return "adminHome";
    } else if (userLogin.getUserId().equalsIgnoreCase("Brijeshwar") &&
        userLogin.getPassword().equalsIgnoreCase("123")) {
      return "userHome";
    } else {
      return "error";
    }

  }
}

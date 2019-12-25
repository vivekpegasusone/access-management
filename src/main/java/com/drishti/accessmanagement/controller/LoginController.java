package com.drishti.accessmanagement.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

@Controller
public class LoginController {

  private static final String VIEW_NAME = "login";

  @GetMapping(value = { "/", "/login" })
  public String loginPage() {
    return VIEW_NAME;
  }

  @GetMapping(value = "/loginFailure")
  public String loginFailure(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false);
    String errorMessage = null;
    if (nonNull(session)) {
      AuthenticationException ex = (AuthenticationException) session
          .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
      if (nonNull(ex)) {
        errorMessage = handleException(ex);
      }
    }
    model.addAttribute("errorMessage", errorMessage);
    return VIEW_NAME;
  }

  private String handleException(Exception exception) {

    if (exception instanceof BadCredentialsException) {
      return "Invalid username or password. Please try again.";
    } else {
      return exception.getMessage();
    }
  }
}

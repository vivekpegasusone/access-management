package com.drishti.accessmanagement.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.drishti.accessmanagement.dto.role.RoleView;
import com.drishti.accessmanagement.dto.user.UserView;
import com.drishti.accessmanagement.service.role.RoleService;
import com.drishti.accessmanagement.service.user.UserService;

import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_LIST_USERS;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_CREATE_USER;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  @InitBinder(value = "userView")
  private void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, stringtrimmer);
  }

  @GetMapping(value = "/create")
  public ModelAndView showCreateUser() {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_USER);

    List<RoleView> roles = roleService.getRoles();

    modelAndView.addObject("roles", roles);
    modelAndView.addObject("userView", new UserView());

    return modelAndView;
  }

  @PostMapping(value = "/save")
  public ModelAndView saveUser(@Valid UserView userView, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_USER);

    if (bindingResult.hasErrors()) {
      modelAndView.addObject("userView", userView);
    } else {
      UserView newUser = userService.createUser(userView);
      List<RoleView> roles = roleService.getRoles();

      modelAndView.addObject("roles", roles);
      modelAndView.addObject("userView", new UserView());
      modelAndView.addObject("createdUser", newUser);
    }

    return modelAndView;
  }

  @GetMapping(value = "/list")
  public ModelAndView listUsers() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_USERS);
    List<UserView> users = userService.findAll();
   
    if(Objects.nonNull(users)) {
      modelAndView.addObject("users", users);
    } else {
      modelAndView.addObject("message", "No user records found.");
    }
  
    return modelAndView;
  }
  
  @GetMapping(value = "/listActive")
  public ModelAndView listActiveUsers() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_USERS);
    List<UserView> users = userService.findActiveUsers();
   
    if(Objects.nonNull(users)) {
      modelAndView.addObject("users", users);
    } else {
      modelAndView.addObject("message", "No user records found.");
    }
  
    return modelAndView;
  }
  
  @GetMapping(value = "/listInActive")
  public ModelAndView listInActiveUsers() {
    List<UserView> users = userService.findInActiveUsers();
    ModelMap modelMap = new ModelMap("users", users);
     
    return prepareModelAndView(VIEW_LIST_USERS, modelMap);
  }
  
  private ModelAndView prepareModelAndView(String viewName, ModelMap modelMap){
    ModelAndView modelAndView = new ModelAndView(viewName);
       
    if(Objects.nonNull(modelMap)) {
      modelAndView.getModelMap().addAttribute(modelAndView);
    } else {
      modelAndView.addObject("message", "No user records found.");
    }
  
    return modelAndView;
  }
}

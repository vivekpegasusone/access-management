package com.drishti.accessmanagement.controller.web.user;

import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_CREATE_USER;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_LIST_USERS;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.drishti.accessmanagement.controller.web.converter.ApplicationVOConverter;
import com.drishti.accessmanagement.controller.web.converter.RoleVOConverter;
import com.drishti.accessmanagement.controller.web.utils.ApplicationUtil;
import com.drishti.accessmanagement.controller.web.utils.UserUtil;
import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.controller.web.view.role.RoleVO;
import com.drishti.accessmanagement.controller.web.view.user.UserVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.user.UserDto;
import com.drishti.accessmanagement.service.application.ApplicationService;
import com.drishti.accessmanagement.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  private static final String MODEL_NAME = "userVO";
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private ApplicationService applicationService;

  @InitBinder(value = MODEL_NAME)
  private void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, stringtrimmer);
    binder.registerCustomEditor(RoleVO.class, "roleVO", new RoleVOConverter());
    binder.registerCustomEditor(ApplicationVO.class, "applicationVO", new ApplicationVOConverter());
  }

  @GetMapping(value = "/create")
  public ModelAndView showCreateUser() {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_USER);

    List<ApplicationDto> applications = applicationService.findActiveApplications();
    populateUserView(modelAndView, applications);
    return modelAndView;
  }
  
  @GetMapping(value = "/edit")
  public ModelAndView showEditUser(@RequestParam("userId") long userId) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_USER);
    
    UserDto userDto = userService.findUserById(userId);
    List<ApplicationDto> applications = applicationService.findActiveApplications();
    populateUserView(modelAndView, userDto, applications);

    return modelAndView;
  }

  @PostMapping(value = "/save")
  public ModelAndView saveUser(@Valid UserVO userVO, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_USER);

    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    List<ApplicationVO> applications = ApplicationUtil.toApplicationViews(applicationDtoList);
    modelAndView.addObject("applicationVOList", applications);    
    
    if (bindingResult.hasErrors()) {
      modelAndView.addObject("userView", userVO);
    } else {
      UserDto userDto = UserUtil.toUserDto(userVO);
      
      if(Objects.isNull(userDto.getId())) {
        userService.createUser(userDto);
        modelAndView.addObject("message", "User record saved successfully.");
      } else {
        userService.updateUser(userDto);
        modelAndView.addObject("message", "User record updated successfully.");
      }
      modelAndView.addObject(MODEL_NAME, new UserVO());
    }

    return modelAndView;
  }

  @GetMapping(value = "/list")
  public ModelAndView listUsers() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_USERS);
    List<UserDto> users = userService.findAll();

    if (!users.isEmpty()) {
      modelAndView.addObject("users", users);
    } else {
      modelAndView.addObject("message", "No user record found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listActive")
  public ModelAndView listActiveUsers() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_USERS);
    List<UserDto> users = userService.findActiveUsers();

    if (!users.isEmpty()) {
      modelAndView.addObject("users", UserUtil.toUserViews(users));
    } else {
      modelAndView.addObject("message", "No active user record found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listInActive")
  public ModelAndView listInActiveUsers() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_USERS);
    List<UserDto> users = userService.findInActiveUsers();
    
    if (!users.isEmpty()) {
      modelAndView.addObject("users", UserUtil.toUserViews(users));
    } else {
      modelAndView.addObject("message", "No in active user record found.");
    }
    
    return modelAndView;
  }
  
  private void populateUserView(ModelAndView modelAndView, List<ApplicationDto> applications) {
    populateUserView(modelAndView, null, applications);
  }
  
  private void populateUserView(ModelAndView modelAndView, UserDto userDto, List<ApplicationDto> applications) {      
    UserVO userVO = null;
    if (Objects.nonNull(userDto)) {
      userVO = UserUtil.toUserView(userDto);
    } else {
      userVO = new UserVO();
    }
    
    List<ApplicationVO> appVOs = ApplicationUtil.toApplicationViews(applications);
   
    modelAndView.addObject(MODEL_NAME, userVO);
    modelAndView.addObject("applicationVOList", appVOs);
  }
  
//  private void populateUserView(ModelAndView modelAndView, List<RoleDto> roles, List<ApplicationDto> applications) {
//    populateUserView(modelAndView, null, roles, applications);
//  }
//  
//  private void populateUserView(ModelAndView modelAndView, UserDto userDto, List<RoleDto> roles, List<ApplicationDto> applications) {      
//    UserVO userVO = null;
//    if (Objects.nonNull(userDto)) {
//      userVO = TO_USER_VIEW.apply(userDto);
//    } else {
//      userVO = new UserVO();
//    }
//    List<RoleVO> roleVOs = TO_ROLE_VIEWS.apply(roles);
//    List<ApplicationVO> appVOs = TO_APPLICATION_VIEWS.apply(applications);
//    
//    modelAndView.addObject("userView", userVO);
//    modelAndView.addObject("roleVOList", roleVOs);
//    modelAndView.addObject("applicationVOList", appVOs);    
//  }
}

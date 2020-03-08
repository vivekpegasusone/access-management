package com.drishti.accessmanagement.controller.web.application;

import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_CREATE_APPLICATION;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_INACTIVE_APPLICATION;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_LIST_APPLICATION;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.drishti.accessmanagement.controller.web.utils.ApplicationUtil;
import com.drishti.accessmanagement.controller.web.view.OptionView;
import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.service.application.ApplicationService;
import com.drishti.accessmanagement.service.role.RoleService;

@Controller
@RequestMapping("/application")
public class ApplicationController {

  private static final String MODEL_NAME = "applicationVO";
  
  @Autowired
  private RoleService roleService;
  
  @Autowired
  private ApplicationService applicationService;

  @InitBinder(value = MODEL_NAME)
  private void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, stringtrimmer);
  }

  @GetMapping(value = "/create")
  public ModelAndView showCreateUser() {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_APPLICATION);
    modelAndView.addObject(MODEL_NAME, new ApplicationVO());
    
    return modelAndView;
  }
  
  @GetMapping(value = "/edit")
  public ModelAndView showEditUser(@RequestParam("applicationId") long applicationId) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_APPLICATION);
    ApplicationDto applicationDto = applicationService.findApplicationById(applicationId);
    
    modelAndView.addObject(MODEL_NAME, ApplicationUtil.toApplicationView(applicationDto));    
    return modelAndView;
  }

  @PostMapping(value = "/save")
  public ModelAndView saveApplication(@Valid ApplicationVO applicationVO, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_APPLICATION);

    if (bindingResult.hasErrors()) {
      modelAndView.addAllObjects(bindingResult.getModel());
    } else {
      ApplicationDto applicationDto = ApplicationUtil.toApplicationDto(applicationVO);
      
      if(Objects.isNull(applicationDto.getId())) {
        applicationDto = applicationService.createApplication(applicationDto);
        modelAndView.addObject("message", "Application record saved successfully.");
      } else {
        applicationDto = applicationService.updateApplication(applicationDto);
        modelAndView.addObject("message", "Application record updated successfully.");
      }

      modelAndView.addObject(MODEL_NAME, new ApplicationVO());
   }

    return modelAndView;
  }

  @GetMapping(value = "/list")
  public ModelAndView listApplications() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_APPLICATION);
    List<ApplicationDto> applications = applicationService.findAll();

    if (!applications.isEmpty()) {
      modelAndView.addObject("applicationVOList", ApplicationUtil.toApplicationViews(applications));
    } else {
      modelAndView.addObject("message", "No appliction records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listActive")
  public ModelAndView listActiveApplications() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_APPLICATION);
    List<ApplicationDto> applications = applicationService.findActiveApplications();

    if (!applications.isEmpty()) {
      modelAndView.addObject("applicationVOList", ApplicationUtil.toApplicationViews(applications));
    } else {
      modelAndView.addObject("message", "No active application record found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listInActive")
  public ModelAndView listInActiveApplications() {
    ModelAndView modelAndView = new ModelAndView(VIEW_INACTIVE_APPLICATION);
    List<ApplicationDto> applications = applicationService.findInActiveApplications();

    if (!applications.isEmpty()) {
      modelAndView.addObject("applicationVOList", ApplicationUtil.toApplicationViews(applications));
    } else {
      modelAndView.addObject("message", "No inactive application record found.");
    }

    return modelAndView;
  }
  
  @GetMapping(value = "/delete")
  public ModelAndView delete(@RequestParam("applicationId") long applicationId) {
    applicationService.deleteApplicationById(applicationId);
    return listActiveApplications();
  }
  
  @GetMapping(value = "/roles")
  @ResponseBody
  public List<OptionView> getRolesForApplication(@RequestParam("applicationId") long applicationId, HttpServletRequest request,
      HttpServletResponse response) {
    List<RoleDto> roleDtoList = roleService.findRolesByApplicationId(applicationId);
    List<OptionView> optionViewList = roleDtoList.stream().map(r -> new OptionView(r.getId(), r.getName())).collect(Collectors.toList());
    return optionViewList;
  }
}

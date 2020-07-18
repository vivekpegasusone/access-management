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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.permission.PermissionDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;
import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.service.action.ActionService;
import com.drishti.accessmanagement.service.application.ApplicationService;
import com.drishti.accessmanagement.service.permission.PermissionService;
import com.drishti.accessmanagement.service.resource.ResourceService;
import com.drishti.accessmanagement.service.role.RoleService;

@Controller
@RequestMapping("/application")
public class ApplicationController {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
  private static final String MODEL_NAME = "applicationVO";
  
  @Autowired
  private RoleService roleService;
  
  @Autowired
  private ActionService actionService;
  
  @Autowired
  private ResourceService resourceService;
  
  @Autowired
  private PermissionService permissionService;
  
  @Autowired
  private ApplicationService applicationService;

  @InitBinder(value = MODEL_NAME)
  private void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, stringtrimmer);
  }

  @GetMapping(value = "/create")
  public ModelAndView showCreateUser() throws Exception {
    logger.info("Create application requested.");
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_APPLICATION);
    modelAndView.addObject(MODEL_NAME, new ApplicationVO());
    
    return modelAndView;
  }
  
  @GetMapping(value = "/edit")
  public ModelAndView showEditUser(@RequestParam("applicationId") long applicationId) {
    logger.info("Edit application requested for id {}.", applicationId);
    
    ModelAndView modelAndView = null;     
    try {
      modelAndView = new ModelAndView(VIEW_CREATE_APPLICATION);
      ApplicationDto applicationDto = applicationService.findApplicationById(applicationId);
      modelAndView.addObject(MODEL_NAME, ApplicationUtil.toApplicationView(applicationDto));    
    } catch (RecordNotFoundException e) {
      modelAndView = listActiveApplications();
      modelAndView.addObject("message", e.getMessage());
    }    
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
        try {
          applicationService.createApplication(applicationDto);
          modelAndView.addObject("message", "Application record saved successfully.");
        } catch (DuplicateRecordException e) {
          modelAndView.addObject("message", e.getMessage());
        }        
      } else {
        try {
          applicationService.updateApplication(applicationDto);
          modelAndView.addObject("message", "Application record updated successfully.");
        } catch (RecordNotFoundException | DuplicateRecordException e) {
          modelAndView.addObject("message", e.getMessage());
        }        
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
    ModelAndView modelAndView = null;
    try {
      applicationService.deleteApplicationById(applicationId);
      modelAndView = listActiveApplications();
    } catch (RecordNotFoundException e) {
      modelAndView = listActiveApplications();
      modelAndView.addObject("message", e.getMessage());
    }
    return modelAndView;
  }
  
  @GetMapping(value = "/roles")
  @ResponseBody
  public List<OptionView> getRolesForApplication(@RequestParam("applicationId") long applicationId, HttpServletRequest request,
      HttpServletResponse response) {
    List<RoleDto> roleDtoList = roleService.findRolesByApplicationId(applicationId);
    List<OptionView> optionViewList = roleDtoList.stream().map(r -> new OptionView(r.getId(), r.getName())).collect(Collectors.toList());
    return optionViewList;
  }
  
  @GetMapping(value = "/actions")
  @ResponseBody
  public List<OptionView> getActionsForApplication(@RequestParam("applicationId") long applicationId, HttpServletRequest request,
      HttpServletResponse response) {
    List<ActionDto> actionDtoList = actionService.findActionsByApplicationId(applicationId);
    List<OptionView> optionViewList = actionDtoList.stream().map(a -> new OptionView(a.getId(), a.getName())).collect(Collectors.toList());
    return optionViewList;
  }
  
  @GetMapping(value = "/resources")
  @ResponseBody
  public List<OptionView> getResourcesForApplication(@RequestParam("applicationId") long applicationId, HttpServletRequest request,
      HttpServletResponse response) {
    List<ResourceDto> resourceDtoList = resourceService.findResourcesByApplicationId(applicationId);
    List<OptionView> optionViewList = resourceDtoList.stream().map(r -> new OptionView(r.getId(), r.getName())).collect(Collectors.toList());
    return optionViewList;
  }

  @GetMapping(value = "/permissions")
  @ResponseBody
  public List<OptionView> getPermissionsForApplication(@RequestParam("applicationId") long applicationId, HttpServletRequest request,
      HttpServletResponse response) {
    List<PermissionDto> permissionDtoList = permissionService.findPermissionsByApplicationId(applicationId);
    List<OptionView> optionViewList = permissionDtoList.stream().map(r -> new OptionView(r.getId(), r.getName())).collect(Collectors.toList());
    return optionViewList;
  }
}

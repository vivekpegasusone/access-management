package com.drishti.accessmanagement.controller.web.role;

import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_CREATE_ROLE;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_INACTIVE_ROLE;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_LIST_ROLE;

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
import com.drishti.accessmanagement.controller.web.utils.ApplicationUtil;
import com.drishti.accessmanagement.controller.web.utils.RoleUtil;
import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.controller.web.view.role.RoleVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.service.application.ApplicationService;
import com.drishti.accessmanagement.service.role.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

  private static final String MODEL_NAME = "roleVO";
  
  @Autowired
  private RoleService roleService;

  @Autowired
  private ApplicationService applicationService;

  @InitBinder(value = MODEL_NAME)
  private void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, stringtrimmer);
    binder.registerCustomEditor(ApplicationVO.class, "applicationVO", new ApplicationVOConverter());
  }

  @GetMapping(value = "/create")
  public ModelAndView showCreateRole() {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_ROLE);

    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populateRoleView(modelAndView, applicationDtoList);

    return modelAndView;
  }

  @GetMapping(value = "/edit")
  public ModelAndView showEditRole(@RequestParam("roleId") long roleId) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_ROLE);
    RoleDto roleDto = roleService.findRoleById(roleId);

    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populateRoleView(modelAndView, roleDto, applicationDtoList);

    return modelAndView;
  }

  @PostMapping(value = "/save")
  public ModelAndView saveRole(@Valid RoleVO roleVO, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_ROLE);
    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populateRoleView(modelAndView, applicationDtoList);
    
    if (bindingResult.hasErrors()) {
      modelAndView.addAllObjects(bindingResult.getModel());
    } else {
      RoleDto roleDto = RoleUtil.toRoleDto(roleVO);

      if (Objects.isNull(roleDto.getId())) {
        roleService.createRole(roleDto);
        modelAndView.addObject("message", "Role record saved successfully.");
      } else {
        roleService.updateRole(roleDto);
        modelAndView.addObject("message", "Role record updated successfully.");
      }
    }    

    return modelAndView;
  }

  @GetMapping(value = "/list")
  public ModelAndView listRoles() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_ROLE);
    List<RoleDto> roleDtoList = roleService.findAll();

    if (!roleDtoList.isEmpty()) {
      modelAndView.addObject("roleVOList", RoleUtil.toRoleViews(roleDtoList));
    } else {
      modelAndView.addObject("message", "No role records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listActive")
  public ModelAndView listActiveRoles() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_ROLE);
    List<RoleDto> roleDtoList = roleService.findActiveRoles();

    if (!roleDtoList.isEmpty()) {
      modelAndView.addObject("roleVOList", RoleUtil.toRoleViews(roleDtoList));
    } else {
      modelAndView.addObject("message", "No active role record found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listInActive")
  public ModelAndView listInActiveRoles() {
    ModelAndView modelAndView = new ModelAndView(VIEW_INACTIVE_ROLE);
    List<RoleDto> roleDtoList = roleService.findInActiveRoles();

    if (!roleDtoList.isEmpty()) {
      modelAndView.addObject("roleVOList", RoleUtil.toRoleViews(roleDtoList));
    } else {
      modelAndView.addObject("message", "No inactive role record found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/delete")
  public ModelAndView delete(@RequestParam("roleId") long roleId) {
    roleService.deleteRoleById(roleId);
    return listActiveRoles();
  }

  private void populateRoleView(ModelAndView modelAndView, List<ApplicationDto> applicationDtoList) {
    populateRoleView(modelAndView, null, applicationDtoList);
  }

  private void populateRoleView(ModelAndView modelAndView, RoleDto roleDto, List<ApplicationDto> applicationDtoList) {
    RoleVO roleVO = null;

    if (Objects.nonNull(roleDto)) {
      roleVO = RoleUtil.toRoleView(roleDto);
    } else {
      roleVO = new RoleVO();
    }
    List<ApplicationVO> appVOs = ApplicationUtil.toApplicationViews(applicationDtoList);

    modelAndView.addObject(MODEL_NAME, roleVO);
    modelAndView.addObject("applicationVOList", appVOs);
  }
}

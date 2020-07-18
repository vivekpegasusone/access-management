package com.drishti.accessmanagement.controller.web.permission;

import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_CREATE_PERMISSION;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_INACTIVE_PERMISSION;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_LIST_PERMISSION;

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

import com.drishti.accessmanagement.controller.web.converter.ActionVOConverter;
import com.drishti.accessmanagement.controller.web.converter.ApplicationVOConverter;
import com.drishti.accessmanagement.controller.web.converter.ResourceVOConverter;
import com.drishti.accessmanagement.controller.web.utils.ApplicationUtil;
import com.drishti.accessmanagement.controller.web.utils.PermissionUtil;
import com.drishti.accessmanagement.controller.web.view.action.ActionVO;
import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.controller.web.view.permission.PermissionVO;
import com.drishti.accessmanagement.controller.web.view.resource.ResourceVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.permission.PermissionDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.service.application.ApplicationService;
import com.drishti.accessmanagement.service.permission.PermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {

  private static final String MODEL_NAME = "permissionVO";

  @Autowired
  private PermissionService permissionService;

  @Autowired
  private ApplicationService applicationService;

  @InitBinder(value = MODEL_NAME)
  private void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, stringtrimmer);
    binder.registerCustomEditor(ActionVO.class, "actionVO", new ActionVOConverter());
    binder.registerCustomEditor(ResourceVO.class, "resourceVO", new ResourceVOConverter());
    binder.registerCustomEditor(ApplicationVO.class, "applicationVO", new ApplicationVOConverter());
  }

  @GetMapping(value = "/create")
  public ModelAndView showCreatePermission() {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_PERMISSION);
    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populatePermissionView(modelAndView, applicationDtoList);

    return modelAndView;
  }

  @GetMapping(value = "/edit")
  public ModelAndView showEditPermission(@RequestParam("permissionId") long permissionId) {
    ModelAndView modelAndView = null;
    
    try {
      modelAndView = new ModelAndView(VIEW_CREATE_PERMISSION);
      PermissionDto permissionDto = permissionService.findPermissionById(permissionId);
      List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
      populatePermissionView(modelAndView, permissionDto, applicationDtoList);
    } catch (RecordNotFoundException e) {
      modelAndView = listActivePermissions();
      modelAndView.addObject("message", e.getMessage());
    }   

    return modelAndView;
  }

  @PostMapping(value = "/save")
  public ModelAndView savePermission(@Valid PermissionVO permissionVO, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_PERMISSION);
    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populatePermissionView(modelAndView, applicationDtoList);

    if (bindingResult.hasErrors()) {
      modelAndView.addAllObjects(bindingResult.getModel());
    } else {
      PermissionDto permissionDto = PermissionUtil.toPermissionDto(permissionVO);

      if (Objects.isNull(permissionDto.getId())) {
        try {
          permissionService.createPermission(permissionDto);
          modelAndView.addObject("message", "Permission record saved successfully.");
        } catch (DuplicateRecordException e) {
          modelAndView.addObject("message", e.getMessage());
        }       
      } else {
        try {
          permissionService.updatePermission(permissionDto);
          modelAndView.addObject("message", "Permission record updated successfully.");
        } catch (RecordNotFoundException | DuplicateRecordException e) {
          modelAndView.addObject("message", e.getMessage());
        }        
      }
    }

    return modelAndView;
  }

  @GetMapping(value = "/list")
  public ModelAndView listPermissions() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_PERMISSION);
    List<PermissionDto> permissionDtoList = permissionService.findAll();

    if (!permissionDtoList.isEmpty()) {
      modelAndView.addObject("permissionVOList", PermissionUtil.toPermissionViews(permissionDtoList));
    } else {
      modelAndView.addObject("message", "No permission records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listActive")
  public ModelAndView listActivePermissions() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_PERMISSION);
    List<PermissionDto> permissionDtoList = permissionService.findActivePermissions();

    if (!permissionDtoList.isEmpty()) {
      modelAndView.addObject("permissionVOList", PermissionUtil.toPermissionViews(permissionDtoList));
    } else {
      modelAndView.addObject("message", "No permission records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listInActive")
  public ModelAndView listInActivePermissions() {
    ModelAndView modelAndView = new ModelAndView(VIEW_INACTIVE_PERMISSION);
    List<PermissionDto> permissionDtoList = permissionService.findInActivePermissions();

    if (!permissionDtoList.isEmpty()) {
      modelAndView.addObject("permissionVOList", PermissionUtil.toPermissionViews(permissionDtoList));
    } else {
      modelAndView.addObject("message", "No permission records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/delete")
  public ModelAndView delete(@RequestParam("permissionId") long permissionId) {
    ModelAndView modelAndView = null;
    try {
      permissionService.deletePermissionById(permissionId);
      modelAndView = listActivePermissions();
    } catch (RecordNotFoundException e) {
      modelAndView = listActivePermissions();
      modelAndView.addObject("message", e.getMessage());
    }
    return listActivePermissions();
  }

  private void populatePermissionView(ModelAndView modelAndView, List<ApplicationDto> applicationDtoList) {
    populatePermissionView(modelAndView, null, applicationDtoList);
  }

  private void populatePermissionView(ModelAndView modelAndView, PermissionDto permissionDto,
      List<ApplicationDto> applicationDtoList) {
    PermissionVO permissionVO = null;

    if (Objects.nonNull(permissionDto)) {
      permissionVO = PermissionUtil.toPermissionView(permissionDto);
    } else {
      permissionVO = new PermissionVO();
    }
    List<ApplicationVO> appVOs = ApplicationUtil.toApplicationViews(applicationDtoList);

    modelAndView.addObject(MODEL_NAME, permissionVO);
    modelAndView.addObject("applicationVOList", appVOs);
  }
}

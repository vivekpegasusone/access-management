package com.drishti.accessmanagement.controller.web.action;

import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_CREATE_ACTION;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_INACTIVE_ACTION;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_LIST_ACTION;

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
import com.drishti.accessmanagement.controller.web.utils.ActionUtil;
import com.drishti.accessmanagement.controller.web.utils.ApplicationUtil;
import com.drishti.accessmanagement.controller.web.view.action.ActionVO;
import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.service.action.ActionService;
import com.drishti.accessmanagement.service.application.ApplicationService;

@Controller
@RequestMapping("/action")
public class ActionController {

  private static final String MODEL_NAME = "actionVO";

  @Autowired
  private ActionService actionService;

  @Autowired
  private ApplicationService applicationService;

  @InitBinder(value = MODEL_NAME)
  private void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, stringtrimmer);
    binder.registerCustomEditor(ApplicationVO.class, "applicationVO", new ApplicationVOConverter());
  }

  @GetMapping(value = "/create")
  public ModelAndView showCreateAction() {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_ACTION);
    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populateActionView(modelAndView, applicationDtoList);

    return modelAndView;
  }

  @GetMapping(value = "/edit")
  public ModelAndView showEditAction(@RequestParam("actionId") long actionId) {
    ModelAndView modelAndView = null; 
    try {
      ActionDto actionDto = actionService.findActionById(actionId);
      modelAndView = new ModelAndView(VIEW_CREATE_ACTION);
      
      List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
      populateActionView(modelAndView, actionDto, applicationDtoList);      
    } catch (RecordNotFoundException e) {
      modelAndView = listActiveActions();
      modelAndView.addObject("message", e.getMessage());
    }
    
    return modelAndView;
  }

  @PostMapping(value = "/save")
  public ModelAndView saveAction(@Valid ActionVO actionVO, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_ACTION);
    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populateActionView(modelAndView, applicationDtoList);
    
    if (bindingResult.hasErrors()) {
      modelAndView.addAllObjects(bindingResult.getModel());
    } else {
      ActionDto actionDto = ActionUtil.toActionDto(actionVO);

      if (Objects.isNull(actionDto.getId())) {
        try {
          actionService.createAction(actionDto);
          modelAndView.addObject("message", "Action record saved successfully.");
        } catch (DuplicateRecordException e) {
          modelAndView.addObject("message", e.getMessage());
        }        
      } else {
        try {
          actionService.updateAction(actionDto);
          modelAndView.addObject("message", "Action record updated successfully.");
        } catch (RecordNotFoundException | DuplicateRecordException e) {
          modelAndView.addObject("message", e.getMessage());
        }        
      }
    }
    
    return modelAndView;
  }

  @GetMapping(value = "/list")
  public ModelAndView listActions() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_ACTION);
    List<ActionDto> actionDtoList = actionService.findAll();

    if (!actionDtoList.isEmpty()) {
      modelAndView.addObject("actionVOList", ActionUtil.toActionViews(actionDtoList));
    } else {
      modelAndView.addObject("message", "No action records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listActive")
  public ModelAndView listActiveActions() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_ACTION);
    List<ActionDto> actionDtoList = actionService.findActiveActions();

    if (!actionDtoList.isEmpty()) {
      modelAndView.addObject("actionVOList", ActionUtil.toActionViews(actionDtoList));
    } else {
      modelAndView.addObject("message", "No action records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listInActive")
  public ModelAndView listInActiveActions() {
    ModelAndView modelAndView = new ModelAndView(VIEW_INACTIVE_ACTION);
    List<ActionDto> actionDtoList = actionService.findInActiveActions();

    if (!actionDtoList.isEmpty()) {
      modelAndView.addObject("actionVOList", ActionUtil.toActionViews(actionDtoList));
    } else {
      modelAndView.addObject("message", "No action records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/delete")
  public ModelAndView delete(@RequestParam("actionId") long actionId) {
    ModelAndView modelAndView = null;
    try {
      actionService.deleteActionById(actionId);
      modelAndView = listActiveActions();
    } catch (RecordNotFoundException e) {
      modelAndView = listActiveActions();
      modelAndView.addObject("message", e.getMessage());
    }
    return modelAndView;
  }

  private void populateActionView(ModelAndView modelAndView, List<ApplicationDto> applicationDtoList) {
    populateActionView(modelAndView, null, applicationDtoList);
  }

  private void populateActionView(ModelAndView modelAndView, ActionDto actionDto,
      List<ApplicationDto> applicationDtoList) {
    ActionVO actionVO = null;

    if (Objects.nonNull(actionDto)) {
      actionVO = ActionUtil.toActionView(actionDto);
    } else {
      actionVO = new ActionVO();
    }
    List<ApplicationVO> appVOs = ApplicationUtil.toApplicationViews(applicationDtoList);

    modelAndView.addObject(MODEL_NAME, actionVO);
    modelAndView.addObject("applicationVOList", appVOs);
  }
}

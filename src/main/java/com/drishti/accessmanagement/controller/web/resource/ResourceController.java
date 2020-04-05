package com.drishti.accessmanagement.controller.web.resource;

import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_CREATE_RESOURCE;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_INACTIVE_RESOURCE;
import static com.drishti.accessmanagement.controller.ControllerConstants.VIEW_LIST_RESOURCE;

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
import com.drishti.accessmanagement.controller.web.utils.ResourceUtil;
import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.controller.web.view.resource.ResourceVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;
import com.drishti.accessmanagement.service.application.ApplicationService;
import com.drishti.accessmanagement.service.resource.ResourceService;

@Controller
@RequestMapping("/resource")
public class ResourceController {

  private static final String MODEL_NAME = "resourceVO";

  @Autowired
  private ResourceService resourceService;

  @Autowired
  private ApplicationService applicationService;

  @InitBinder(value = MODEL_NAME)
  private void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, stringtrimmer);
    binder.registerCustomEditor(ApplicationVO.class, "applicationVO", new ApplicationVOConverter());
  }

  @GetMapping(value = "/create")
  public ModelAndView showCreateResource() {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_RESOURCE);
    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populateResourceView(modelAndView, applicationDtoList);

    return modelAndView;
  }

  @GetMapping(value = "/edit")
  public ModelAndView showEditResource(@RequestParam("resourceId") long resourceId) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_RESOURCE);
    ResourceDto resourceDto = resourceService.findResourceById(resourceId);

    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populateResourceView(modelAndView, resourceDto, applicationDtoList);

    return modelAndView;
  }

  @PostMapping(value = "/save")
  public ModelAndView saveResource(@Valid ResourceVO resourceVO, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView(VIEW_CREATE_RESOURCE);
    List<ApplicationDto> applicationDtoList = applicationService.findActiveApplications();
    populateResourceView(modelAndView, applicationDtoList);
    
    if (bindingResult.hasErrors()) {
      modelAndView.addAllObjects(bindingResult.getModel());
    } else {
      ResourceDto resourceDto = ResourceUtil.toResourceDto(resourceVO);

      if (Objects.isNull(resourceDto.getId())) {
        resourceService.createResource(resourceDto);
        modelAndView.addObject("message", "Resource record saved successfully.");
      } else {
        resourceService.updateResource(resourceDto);
        modelAndView.addObject("message", "Resource record updated successfully.");
      }
    }
    
    return modelAndView;
  }

  @GetMapping(value = "/list")
  public ModelAndView listResources() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_RESOURCE);
    List<ResourceDto> resourceDtoList = resourceService.findAll();

    if (!resourceDtoList.isEmpty()) {
      modelAndView.addObject("resourceVOList", ResourceUtil.toResourceViews(resourceDtoList));
    } else {
      modelAndView.addObject("message", "No resource records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listActive")
  public ModelAndView listActiveResources() {
    ModelAndView modelAndView = new ModelAndView(VIEW_LIST_RESOURCE);
    List<ResourceDto> resourceDtoList = resourceService.findActiveResources();

    if (!resourceDtoList.isEmpty()) {
      modelAndView.addObject("resourceVOList", ResourceUtil.toResourceViews(resourceDtoList));
    } else {
      modelAndView.addObject("message", "No resource records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/listInActive")
  public ModelAndView listInActiveResources() {
    ModelAndView modelAndView = new ModelAndView(VIEW_INACTIVE_RESOURCE);
    List<ResourceDto> resourceDtoList = resourceService.findInActiveResources();

    if (!resourceDtoList.isEmpty()) {
      modelAndView.addObject("resourceVOList", ResourceUtil.toResourceViews(resourceDtoList));
    } else {
      modelAndView.addObject("message", "No resource records found.");
    }

    return modelAndView;
  }

  @GetMapping(value = "/delete")
  public ModelAndView delete(@RequestParam("resourceId") long resourceId) {
    resourceService.deleteResourceById(resourceId);
    return listActiveResources();
  }

  private void populateResourceView(ModelAndView modelAndView, List<ApplicationDto> applicationDtoList) {
    populateResourceView(modelAndView, null, applicationDtoList);
  }

  private void populateResourceView(ModelAndView modelAndView, ResourceDto resourceDto,
      List<ApplicationDto> applicationDtoList) {
    ResourceVO resourceVO = null;

    if (Objects.nonNull(resourceDto)) {
      resourceVO = ResourceUtil.toResourceView(resourceDto);
    } else {
      resourceVO = new ResourceVO();
    }
    List<ApplicationVO> appVOs = ApplicationUtil.toApplicationViews(applicationDtoList);

    modelAndView.addObject(MODEL_NAME, resourceVO);
    modelAndView.addObject("applicationVOList", appVOs);
  }
}

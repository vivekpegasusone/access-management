package com.drishti.accessmanagement.controller.web.utils;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import com.drishti.accessmanagement.controller.web.view.resource.ResourceVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;

public class ResourceUtil {
  
  public static ResourceVO toResourceView(ResourceDto resourceDto) {
    ResourceVO resourceVO = null;
    
    if (nonNull(resourceDto)) {
      resourceVO = new ResourceVO();
      resourceVO.setId(resourceDto.getId());
      resourceVO.setName(resourceDto.getName());
      resourceVO.setDescription(resourceDto.getDescription());
      resourceVO.setActive(resourceDto.isActive());
      resourceVO.setCreatedBy(resourceDto.getCreatedBy());
      resourceVO.setUpdatedBy(resourceDto.getUpdatedBy());
      resourceVO.setCreatedOn(resourceDto.getCreatedOn());
      resourceVO.setUpdatedOn(resourceDto.getUpdatedOn());

      resourceVO.setApplicationVO(ApplicationUtil.toApplicationView(resourceDto.getApplicationDto()));
    }
    
    return resourceVO;
  }
  
  public static ResourceDto toResourceDto(ResourceVO resourceView) {
    ResourceDto resourceDto = new ResourceDto(resourceView.getId(), resourceView.getName(), resourceView.getDescription(), resourceView.isActive());

    ApplicationDto appDto = new ApplicationDto();
    appDto.setId(resourceView.getApplicationVO().getId());
    resourceDto.setApplicationDto(ApplicationUtil.toApplicationDto(resourceView.getApplicationVO()));

    return resourceDto;

  };
  
  public static List<ResourceVO> toResourceViews(List<ResourceDto> resourceDtos) {
    List<ResourceVO> resourceViews = null;
    if(nonNull(resourceDtos) && !resourceDtos.isEmpty()) {
      resourceViews = resourceDtos.stream().map(r -> toResourceView(r)).collect(Collectors.toList());
    }
    
    return resourceViews;
  }  
}

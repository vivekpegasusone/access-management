package com.drishti.accessmanagement.controller.web.utils;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import com.drishti.accessmanagement.controller.web.view.application.ApplicationVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;

public class ApplicationUtil {

  public static ApplicationVO toApplicationView(ApplicationDto applicationDto) {
    ApplicationVO appView = null;
    if (nonNull(applicationDto)) {
      appView = new ApplicationVO();
      appView.setId(applicationDto.getId());
      appView.setName(applicationDto.getName());
      appView.setDescription(applicationDto.getDescription());
      appView.setActive(applicationDto.isActive());
      appView.setCreatedBy(applicationDto.getCreatedBy());
      appView.setUpdatedBy(applicationDto.getUpdatedBy());
      appView.setCreatedOn(applicationDto.getCreatedOn());
      appView.setUpdatedOn(applicationDto.getUpdatedOn());
    }
    return appView;
  };
  
  public static ApplicationDto toApplicationDto(ApplicationVO applicationView) {
    return new ApplicationDto(applicationView.getId(), applicationView.getName(),
        applicationView.getDescription(), applicationView.isActive(), applicationView.getCreatedBy(),
        applicationView.getUpdatedBy(), applicationView.getCreatedOn(), applicationView.getUpdatedOn());
  };
 
  public static List<ApplicationVO> toApplicationViews(List<ApplicationDto> applicationDtos) {
    List<ApplicationVO> applicationViews = null;
    if(nonNull(applicationDtos) && !applicationDtos.isEmpty()) {
      applicationViews = applicationDtos.stream().map(a -> toApplicationView(a)).collect(Collectors.toList());
    }
    
    return applicationViews;
  }   
}

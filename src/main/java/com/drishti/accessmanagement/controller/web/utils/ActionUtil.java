package com.drishti.accessmanagement.controller.web.utils;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import com.drishti.accessmanagement.controller.web.view.action.ActionVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.action.ActionDto;

public class ActionUtil {
  
  public static ActionVO toActionView(ActionDto actionDto) {
    ActionVO actionVO = null;
    
    if (nonNull(actionDto)) {
      actionVO = new ActionVO();
      actionVO.setId(actionDto.getId());
      actionVO.setName(actionDto.getName());
      actionVO.setDescription(actionDto.getDescription());
      actionVO.setActive(actionDto.isActive());
      actionVO.setCreatedBy(actionDto.getCreatedBy());
      actionVO.setUpdatedBy(actionDto.getUpdatedBy());
      actionVO.setCreatedOn(actionDto.getCreatedOn());
      actionVO.setUpdatedOn(actionDto.getUpdatedOn());

      actionVO.setApplicationVO(ApplicationUtil.toApplicationView(actionDto.getApplicationDto()));
    }
    
    return actionVO;
  }
  
  public static ActionDto toActionDto(ActionVO actionView) {
    ActionDto actionDto = new ActionDto(actionView.getId(), actionView.getName(), actionView.getDescription(), actionView.isActive());

    ApplicationDto appDto = new ApplicationDto();
    appDto.setId(actionView.getApplicationVO().getId());
    actionDto.setApplicationDto(ApplicationUtil.toApplicationDto(actionView.getApplicationVO()));

    return actionDto;

  };
  
  public static List<ActionVO> toActionViews(List<ActionDto> actionDtos) {
    List<ActionVO> actionViews = null;
    if(nonNull(actionDtos) && !actionDtos.isEmpty()) {
      actionViews = actionDtos.stream().map(r -> toActionView(r)).collect(Collectors.toList());
    }
    
    return actionViews;
  }  
}

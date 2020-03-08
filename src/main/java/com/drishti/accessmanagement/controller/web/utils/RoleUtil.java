package com.drishti.accessmanagement.controller.web.utils;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import com.drishti.accessmanagement.controller.web.view.role.RoleVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.role.RoleDto;

public class RoleUtil {

  public static RoleVO toRoleView(RoleDto roleDto) {
    RoleVO roleVO = null;
    
    if (nonNull(roleDto)) {
      roleVO = new RoleVO();
      roleVO.setId(roleDto.getId());
      roleVO.setName(roleDto.getName());
      roleVO.setDescription(roleDto.getDescription());
      roleVO.setActive(roleDto.isActive());
      roleVO.setCreatedBy(roleDto.getCreatedBy());
      roleVO.setUpdatedBy(roleDto.getUpdatedBy());
      roleVO.setCreatedOn(roleDto.getCreatedOn());
      roleVO.setUpdatedOn(roleDto.getUpdatedOn());

      roleVO.setApplicationVO(ApplicationUtil.toApplicationView(roleDto.getApplicationDto()));
    }
    
    return roleVO;
  }
  
  public static RoleDto toRoleDto(RoleVO roleView) {
    RoleDto roleDto = new RoleDto(roleView.getId(), roleView.getName(), roleView.getDescription(), roleView.isActive());

    ApplicationDto appDto = new ApplicationDto();
    appDto.setId(roleView.getApplicationVO().getId());
    roleDto.setApplicationDto(ApplicationUtil.toApplicationDto(roleView.getApplicationVO()));

    return roleDto;

  };
  
  public static List<RoleVO> toRoleViews(List<RoleDto> roleDtos) {
    List<RoleVO> roleViews = null;
    if(nonNull(roleDtos) && !roleDtos.isEmpty()) {
      roleViews = roleDtos.stream().map(r -> toRoleView(r)).collect(Collectors.toList());
    }
    
    return roleViews;
  }  
}

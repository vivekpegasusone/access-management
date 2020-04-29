package com.drishti.accessmanagement.controller.web.utils;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.drishti.accessmanagement.controller.web.view.permission.PermissionVO;
import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.permission.PermissionDto;
import com.drishti.accessmanagement.dto.resource.ResourceDto;

public class PermissionUtil {

  public static PermissionVO toPermissionView(PermissionDto permissionDto) {
    PermissionVO permissionVO = null;
    
    if (nonNull(permissionDto)) {
      permissionVO = new PermissionVO();
      permissionVO.setId(permissionDto.getId());
      permissionVO.setName(permissionDto.getName());
      permissionVO.setDescription(permissionDto.getDescription());
      permissionVO.setActive(permissionDto.isActive());
      permissionVO.setCreatedBy(permissionDto.getCreatedBy());
      permissionVO.setUpdatedBy(permissionDto.getUpdatedBy());
      permissionVO.setCreatedOn(permissionDto.getCreatedOn());
      permissionVO.setUpdatedOn(permissionDto.getUpdatedOn());

      permissionVO.setActionVO(ActionUtil.toActionView(permissionDto.getActionDto()));
      permissionVO.setResourceVO(ResourceUtil.toResourceView(permissionDto.getResourceDto()));
      
      if(Objects.nonNull(permissionDto.getActionDto())) {
        permissionVO.setApplicationVO(ApplicationUtil.toApplicationView(permissionDto.getActionDto().getApplicationDto()));
      }
    }
    
    return permissionVO;
  }
  
  public static PermissionDto toPermissionDto(PermissionVO permissionView) {
    ActionDto actionDto = new ActionDto(permissionView.getActionVO().getId());
    ResourceDto resourceDto = new ResourceDto(permissionView.getResourceVO().getId());

    PermissionDto permissionDto = new PermissionDto(permissionView.getId(), permissionView.getName(),
        permissionView.getDescription(), permissionView.isActive());
    permissionDto.setActionDto(actionDto);
    permissionDto.setResourceDto(resourceDto);
    
    return permissionDto;
  };
  
  public static List<PermissionVO> toPermissionViews(List<PermissionDto> permissionDtos) {
    List<PermissionVO> permissionViews = null;
    if(nonNull(permissionDtos) && !permissionDtos.isEmpty()) {
      permissionViews = permissionDtos.stream().map(p -> toPermissionView(p)).collect(Collectors.toList());
    }
    
    return permissionViews;
  }  
}

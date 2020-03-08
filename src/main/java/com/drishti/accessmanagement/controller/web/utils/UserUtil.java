package com.drishti.accessmanagement.controller.web.utils;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import com.drishti.accessmanagement.controller.web.view.user.UserVO;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.dto.user.UserDto;

public class UserUtil {

  public static UserVO toUserView(UserDto userDto) {
    UserVO userVO = null;
    
    if (nonNull(userDto)) {
      userVO = new UserVO();
      userVO.setId(userDto.getId());
      userVO.setFirstName(userDto.getFirstName());
      userVO.setLastName(userDto.getLastName());
      userVO.setLoginId(userDto.getLoginId());
      userVO.setEmailId(userDto.getEmailId());
      userVO.setActive(userDto.isActive());
      userVO.setCreatedBy(userDto.getCreatedBy());
      userVO.setUpdatedBy(userDto.getUpdatedBy());
      userVO.setCreatedOn(userDto.getCreatedOn());
      userVO.setUpdatedOn(userDto.getUpdatedOn());

      userVO.setRoleVO(RoleUtil.toRoleView(userDto.getRoleDto()));

      userVO.setApplicationVO(ApplicationUtil.toApplicationView(userDto.getApplicationDto()));
    }
    
    return userVO;
  };
  
  public static UserDto toUserDto(UserVO userView) {
    UserDto userDto = new UserDto(userView.getId(), userView.getLoginId(), userView.getPassword(), 
        userView.getFirstName(), userView.getLastName(), userView.getEmailId(), userView.isActive());
    
    RoleDto roleDto = new RoleDto(userView.getRoleVO().getName());
    roleDto.setId(userView.getRoleVO().getId());
      
    ApplicationDto appDto = new ApplicationDto();
    appDto.setId(userView.getApplicationVO().getId());
     
    userDto.setRoleDto(roleDto);
    userDto.setApplicationDto(appDto);  

    return userDto;
    
  };
  
  public static List<UserVO> toUserViews(List<UserDto> userDtos) {
    List<UserVO> userViews = null;
    if(nonNull(userDtos) && !userDtos.isEmpty()) {
      userViews = userDtos.stream().map(u -> toUserView(u)).collect(Collectors.toList());
    }
    
    return userViews;
  }   
 
}

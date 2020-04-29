package com.drishti.accessmanagement.controller.web.converter;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.drishti.accessmanagement.controller.ControllerConstants;
import com.drishti.accessmanagement.controller.web.view.permission.PermissionVO;

public class PermissionVOConverter extends PropertyEditorSupport {
  
  @Override
  public void setAsText(String text) throws java.lang.IllegalArgumentException {
    if (StringUtils.hasText(text) && !ControllerConstants.HYPHEN.equals(text)) {
      setValue(new PermissionVO(Long.parseLong(text)));
    } else {
      setValue(null);
    }
  }

}

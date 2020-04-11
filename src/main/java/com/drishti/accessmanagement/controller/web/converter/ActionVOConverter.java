package com.drishti.accessmanagement.controller.web.converter;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.drishti.accessmanagement.controller.ControllerConstants;
import com.drishti.accessmanagement.controller.web.view.action.ActionVO;

public class ActionVOConverter extends PropertyEditorSupport {

  @Override
  public void setAsText(String text) throws java.lang.IllegalArgumentException {
    if (StringUtils.hasText(text) && !ControllerConstants.HYPHEN.equals(text)) {
      setValue(new ActionVO(Long.parseLong(text)));
    } else {
      setValue(null);
    }
  }
}

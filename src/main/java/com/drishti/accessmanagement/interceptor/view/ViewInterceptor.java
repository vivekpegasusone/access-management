package com.drishti.accessmanagement.interceptor.view;

import static java.util.Objects.nonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ViewInterceptor implements HandlerInterceptor {

  private static final String VIEW_NAME = "login";
  private static final String TEMPLATE_NAME = "accessManagement";

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    if (nonNull(modelAndView)) {
      String viewName = modelAndView.getViewName();
      if (!VIEW_NAME.equalsIgnoreCase(viewName)) {
        request.setAttribute("contentPage", viewName + ".jsp");
        modelAndView.setViewName(TEMPLATE_NAME);
      }
    }
  }
}

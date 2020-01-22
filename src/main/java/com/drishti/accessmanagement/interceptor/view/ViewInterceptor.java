package com.drishti.accessmanagement.interceptor.view;

import static java.util.Objects.nonNull;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ViewInterceptor implements HandlerInterceptor {

  private static final String JSP_EXT = ".jsp";
  private static final String VIEW_NAME = "login";
  private static final String NAV_USER = "nav/userNav.jsp";
  private static final String NAV_ADMIN = "nav/adminNav.jsp";
  private static final String TEMPLATE_NAME = "accessManagement";

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    if (nonNull(modelAndView)) {
      String viewName = modelAndView.getViewName();
      if (!VIEW_NAME.equalsIgnoreCase(viewName)) {
        request.setAttribute("contentPage", viewName + JSP_EXT);
        modelAndView.setViewName(TEMPLATE_NAME);
      }

      SecurityContext securityContext = SecurityContextHolder.getContext();
      if (nonNull(securityContext)) {
        Authentication authentication = securityContext.getAuthentication();
        if (nonNull(authentication) && authentication.isAuthenticated()) {
          Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
          if (roles.contains("ROLE_ADMIN")) {
            request.setAttribute("navigationPage", NAV_ADMIN);
          } else {
            request.setAttribute("navigationPage", NAV_USER);
          }
        }
      }
    }
  }
}

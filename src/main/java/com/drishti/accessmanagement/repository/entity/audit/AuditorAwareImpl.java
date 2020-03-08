package com.drishti.accessmanagement.repository.entity.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static java.util.Objects.nonNull;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    String loginId = null;
    SecurityContext securityContext = SecurityContextHolder.getContext();
    if (nonNull(securityContext)) {
      Authentication authentication = securityContext.getAuthentication();
      if (nonNull(authentication) && authentication.isAuthenticated()) {
        loginId = authentication.getName();
      }
    }
    return Optional.ofNullable(loginId);
  }
}
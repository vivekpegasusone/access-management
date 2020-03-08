package com.drishti.accessmanagement.config;

import com.drishti.accessmanagement.interceptor.view.ViewInterceptor;
import com.drishti.accessmanagement.repository.entity.audit.AuditorAwareImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ApplicationConfiguration implements WebMvcConfigurer {

  @Autowired
  private ViewInterceptor viewInterceptor;

  @Bean
  public AuditorAware<String> auditorProvider() {
    return new AuditorAwareImpl();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(viewInterceptor);
  }
}

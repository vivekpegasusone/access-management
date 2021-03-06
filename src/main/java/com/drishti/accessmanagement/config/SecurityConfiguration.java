package com.drishti.accessmanagement.config;

import com.drishti.accessmanagement.handler.security.authentication.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private LoginSuccessHandler loginSuccessHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/resources/**").permitAll()
        .anyRequest()
        .authenticated()
      .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/home")
        .usernameParameter("userId")
        .passwordParameter("password")
        .successHandler(loginSuccessHandler)
        .failureUrl("/loginFailure")
        .permitAll()
      .and()
        .logout()
        .logoutUrl("/processLogout")
        .logoutSuccessUrl("/logoutSuccess")
        .permitAll()
      .and()
        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());  
  }

  @Override
  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("vivek").password("{noop}123").roles("ADMIN")
        .and().withUser("amit").password("{noop}123").roles("USER");
  }
}

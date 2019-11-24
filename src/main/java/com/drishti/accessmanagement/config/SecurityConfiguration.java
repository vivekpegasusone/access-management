package com.drishti.accessmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/home")
        .permitAll();
  }

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    User.UserBuilder user = User.withDefaultPasswordEncoder();

    auth.inMemoryAuthentication()
        .withUser(user.username("vivek").password("123").roles("ADMIN"))
        .withUser(user.username("brijeshwar").password("123").roles("USER"));
  }

}

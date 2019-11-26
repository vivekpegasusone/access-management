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
        .usernameParameter("userId")
        .passwordParameter("password")
        .successHandler(new AuthenticationSuccessHandler() {			
          @Override
          public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (roles.contains("ROLE_ADMIN")) {
               response.sendRedirect("/access-management/admin");
            } else {
              response.sendRedirect("/access-management/user");
            }
          }
        }).permitAll();
  }

  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  	  auth.inMemoryAuthentication()
  	  .withUser("vivek").password("{noop}123").roles("ADMIN")
  	  .and().withUser("amit").password("{noop}123").roles("USER");
  } 
}

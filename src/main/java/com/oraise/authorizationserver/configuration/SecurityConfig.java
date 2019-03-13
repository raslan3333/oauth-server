package com.oraise.authorizationserver.configuration;

import com.oraise.authorizationserver.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The type Security config.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  /**
   * Encoder password encoder.
   *
   * @return PasswordEncoder
   */

  private ClientSinglton clientSinglton = ClientSinglton.getInstance();
  @Bean
  public PasswordEncoder encoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  public RedirectionAuthenticationSuccessHandler redirect() {
    return new RedirectionAuthenticationSuccessHandler();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsServiceImpl();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/assets/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests().antMatchers("/login", "/signup", "/")
        .access("hasRole('ANONYMOUS')")
        .antMatchers()
        .access("hasRole('USER')").anyRequest().fullyAuthenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .successHandler(loginSuccessHandler())
        .permitAll()
        .failureHandler(loginFailureHandler())
        .and()
        .logout()
        .permitAll()
        .logoutSuccessUrl("/");
  }

  private AuthenticationSuccessHandler loginSuccessHandler() {
    System.out.println();
    return (request, response, authentication) -> {
      response.sendRedirect(clientSinglton.url);
    };
  }

  private AuthenticationFailureHandler loginFailureHandler() {
    return (request, response, exception) -> {
      response.sendRedirect("/");
    };

  }

  /**
   * Authentication provider dao authentication provider.
   *
   * @return the dao authentication provider
   */
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  /**
   * Password {@link PasswordEncoder}
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


}

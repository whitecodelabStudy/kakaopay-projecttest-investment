package com.kakaopay.project.api.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kakaopay.project.api.auth.JwtAuthenticationEntryPoint;
import com.kakaopay.project.api.auth.filter.JwtAuthenticationFilter;
import com.kakaopay.project.api.auth.service.CustomUserDetailService;

/**
 * Spring web security Config
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * jwtAuthenticationEntryPoint
   */
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  /**
   * jwt filter
   */
  private final JwtAuthenticationFilter authenticationFilter;
  /**
   * token User Service
   */
  private final CustomUserDetailService customUserDetailService;

  /**
   * WebSecurityConfig
   * 
   * @param jwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
   * @param authenticationFilter authenticationFilter
   * @param customUserDetailService customUserDetailService
   */
  @Autowired
  public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      JwtAuthenticationFilter authenticationFilter, CustomUserDetailService customUserDetailService) {
    super();
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.authenticationFilter = authenticationFilter;
    this.customUserDetailService = customUserDetailService;
  }

  /**
   * configureGlobal ????????? custom detail service ?????? ??? ???????????? ????????? ?????? ??????.
   * @param auth
   * @throws Exception
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
  }

  /**
   * web security ??????
   *
   * @param http
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        // cors disable
        .cors().disable()
        // token ?????? url
        .authorizeRequests()
        // ???????????? ??????
        .antMatchers("/auth/authenticate", "/auth/token", "/api/member/signup").permitAll()
        // ????????? ???????????? ?????? ?????? ????????? ?????? ????????? ??????????????? ???
        .anyRequest().authenticated()
        // and
        .and()
        // Token ??? session ??? ?????? ??????. STATELESS ??????
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // and
        .and()
        // exception Handling ??? ????????? ?????? jwtAuthenticationEntryPoint ??????. ?????? ????????? ??????.
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

    // Filter ??????.
    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * ignore check swagger resource
   * @param web web
   */
  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**",
        "/swagger/**");

  }

  /**
   * passwordEncoder ???????????? ?????????
   *
   * @return PasswordEncoder encoding
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}

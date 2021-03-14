package com.kakaopay.project.api.auth.config;

import javax.sql.DataSource;

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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  private final JwtAuthenticationFilter authenticationFilter;

  private final CustomUserDetailService customUserDetailService;

  private final DataSource dataSource;

  @Autowired
  public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      JwtAuthenticationFilter authenticationFilter, CustomUserDetailService customUserDetailService,
      DataSource dataSource) {
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.authenticationFilter = authenticationFilter;
    this.customUserDetailService = customUserDetailService;
    this.dataSource = dataSource;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        // cors disable
        .cors().disable()
        // token 관련 url
        .authorizeRequests()
        // 인증없이 허용
        .antMatchers("/auth/authenticate", "/auth/token", "/api/member/signup").permitAll()
        // 토큰을 활용하는 경우 모든 요청에 대해 접근이 가능하도록 함
        .anyRequest().authenticated()
        // and
        .and()
        // Token 은 session 이 필요 없음. STATELESS 설정
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // and
        .and()
        // exception Handling 을 위해서 실제 jwtAuthenticationEntryPoint 구현. 인증 실패시 적용.
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

    // Filter 설정.
    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  // ignore check swagger resource
  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**",
        "/swagger/**");

  }

}

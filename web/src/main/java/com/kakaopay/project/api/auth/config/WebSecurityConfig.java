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
   * configureGlobal 인증시 custom detail service 사용 및 비민번호 암호화 방식 설정.
   * @param auth
   * @throws Exception
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
  }

  /**
   * web security 설정
   *
   * @param http
   * @throws Exception
   */
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
   * passwordEncoder 패스워드 암호화
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

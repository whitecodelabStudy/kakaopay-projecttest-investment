package com.kakaopay.project.api.auth.config;// package com.wepicksoft.webads.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource);
    auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        // cors disable
        .cors().disable()
        // token 관련 url
        .authorizeRequests().antMatchers("/authenticate").permitAll()
        // 토큰을 활용하는 경우 모든 요청에 대해 접근이 가능하도록 함
        .anyRequest().authenticated()
        // and
        .and()
        // Token은 session이 필요 없음. STATELESS로 설정
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // and
        .and()
        // exceptionHandling을 위해서 실제 jwtAuthenticationEntryPoint 구현. 인증 실패시 적용.
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

    // fitler 설정.
    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

}

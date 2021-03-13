package com.kakaopay.project.web.config;// package com.wepicksoft.webads.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kakaopay.project.api.auth.JwtAuthenticationEntryPoint;
import com.kakaopay.project.api.auth.service.JwtUserDetailsService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @Autowired
  private AuthJwtFilter authJwtFilter;


  @Autowired
  private DataSource dataSource;


  @Override
  public void configure(WebSecurity web) {
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource);
    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers("/authenticate", "/api/member").permitAll()
        // 토큰을 활용하는 경우 모든 요청에 대해 접근이 가능하도록 함
        .anyRequest().authenticated().and()
        // exceptionHandling을 위해서 실제 jwtAuthenticationEntryPoint 구현. 인증 실패시 적용.
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
        // 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // fitler 설정.
    http.addFilter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}

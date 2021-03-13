package com.kakaopay.project.api.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.auth.dto.AuthInvestorDto;
import com.kakaopay.project.api.auth.service.CustomUserDetailService;
import com.kakaopay.project.api.auth.service.JwtUtil;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final CustomUserDetailService customUserDetailService;

  private final JwtUtil jwtUtil;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService,
      JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.customUserDetailService = customUserDetailService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping(value = "/authenticate")
  public ResponseEntity<ApiResponseJson> generateToken(@RequestBody AuthInvestorDto authInvestorDto) {
    try {
      log.debug(authInvestorDto.toString());
      this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authInvestorDto.getInvestId(), authInvestorDto.getPassword()));
      UserDetails userDetails = this.customUserDetailService.loadUserByUsername(authInvestorDto.getInvestId());
      String token = this.jwtUtil.generateToken(userDetails);
      log.debug(token);
      return ResponseEntity.ok(new ApiResponseJson.Builder(token).build());
    } catch (UsernameNotFoundException e) {
      log.error("Investor not found!!", e);
      throw new ApiException();
    } catch (BadCredentialsException be) {
      log.error("", be);
      throw new ApiException();
    }

  }
}

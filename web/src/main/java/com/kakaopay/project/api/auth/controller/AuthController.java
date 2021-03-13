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

import com.kakaopay.project.api.auth.dto.AuthMemberDto;
import com.kakaopay.project.api.auth.service.CustomUserDetailService;
import com.kakaopay.project.api.auth.service.JwtUtil;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.common.exception.ApiException;

import io.swagger.annotations.ApiOperation;
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
  @ApiOperation(value = "token 발급", notes = "사용자 로그인 후 jwt token 을 발급한다.")
  public ResponseEntity<ApiResponseJson> generateToken(@RequestBody AuthMemberDto authMemberDto) {
    try {
      this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authMemberDto.getMemberId(), authMemberDto.getPassword()));
      UserDetails userDetails =
          this.customUserDetailService.loadUserByUsername(String.valueOf(authMemberDto.getMemberId()));
      String token = this.jwtUtil.generateToken(userDetails);
      log.debug(token);
      return ResponseEntity.ok(new ApiResponseJson.Builder(token).build());
    } catch (UsernameNotFoundException e) {
      log.error("Member not found!!", e);
      throw new ApiException(ApiCode.MEMBER_NOT_FOUND, "Member not found!!", e);
    } catch (BadCredentialsException be) {
      log.error("Bad Credentials!!", be);
      throw new ApiException(ApiCode.BAD_CREDENTIALS, "Bad Credentials!!", be);
    }
  }

}

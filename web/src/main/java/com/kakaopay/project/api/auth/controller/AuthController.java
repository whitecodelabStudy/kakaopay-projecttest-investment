package com.kakaopay.project.api.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.auth.dto.AuthMemberDto;
import com.kakaopay.project.api.auth.service.CustomUserDetailService;
import com.kakaopay.project.api.auth.service.JwtTokenProvider;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.common.exception.ApiException;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final CustomUserDetailService customUserDetailService;

  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService,
      JwtTokenProvider jwtTokenProvider) {
    this.authenticationManager = authenticationManager;
    this.customUserDetailService = customUserDetailService;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping(value = "/authenticate")
  @ApiOperation(value = "token 발급", notes = "사용자 로그인 후 jwt token 을 발급한다.")
  public ResponseEntity<ApiResponseJson> generateToken(@RequestBody AuthMemberDto authMemberDto) {
    try {
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
          String.valueOf(authMemberDto.getMemberId()), authMemberDto.getPassword());
      this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
      UserDetails userDetails =
          this.customUserDetailService.loadUserByUsername(String.valueOf(authMemberDto.getMemberId()));
      String token = this.jwtTokenProvider.generateToken(userDetails);
      log.debug("### JWT :: " + token);
      return ResponseEntity.ok(new ApiResponseJson.Builder(Map.of("access_token", token)).build());
    } catch (UsernameNotFoundException e) {
      log.error("Member not found!!", e);
      throw new ApiException(ApiCode.MEMBER_NOT_FOUND, "Member not found!!", e).setHttpStatus(HttpStatus.NO_CONTENT);
    } catch (BadCredentialsException be) {
      log.error("Bad Credentials!!", be);
      throw new ApiException(ApiCode.BAD_CREDENTIALS, "Bad Credentials!!", be).setHttpStatus(HttpStatus.UNAUTHORIZED);
    }
  }

}

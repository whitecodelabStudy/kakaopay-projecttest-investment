package com.kakaopay.project.api.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.auth.dto.AccessTokenDto;
import com.kakaopay.project.api.auth.dto.AuthMemberDto;
import com.kakaopay.project.api.auth.service.CustomUserDetailService;
import com.kakaopay.project.api.auth.service.JwtTokenProvider;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.common.exception.ApiException;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final CustomUserDetailService customUserDetailService;

  private final JwtTokenProvider jwtTokenProvider;

  /**
   * AuthController Constructor
   *
   * @param authenticationManager authenticationManager
   * @param customUserDetailService customUserDetailService
   * @param jwtTokenProvider jwtTokenProvider
   */
  @Autowired
  public AuthController(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService,
      JwtTokenProvider jwtTokenProvider) {
    this.authenticationManager = authenticationManager;
    this.customUserDetailService = customUserDetailService;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  /**
   * generateToken ?????? ??????
   *
   * @param authMemberDto
   * @return
   */
  @PostMapping("/authenticate")
  @ApiOperation(value = "token ??????", notes = "????????? ????????? ??? jwt token ??? ????????????.")
  public ResponseEntity<ApiResponseJson> generateToken(@RequestBody AuthMemberDto authMemberDto) {
    try {
      // id, password ??? ???????????? ??????.
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
          String.valueOf(authMemberDto.getMemberId()), authMemberDto.getPassword());
      this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
      UserDetails userDetails =
          this.customUserDetailService.loadUserByUsername(String.valueOf(authMemberDto.getMemberId()));
      // token ??????
      return ResponseEntity.ok(
          new ApiResponseJson.Builder(new AccessTokenDto(this.jwtTokenProvider.generateToken(userDetails))).build());
    } catch (BadCredentialsException be) {
      log.error("Bad Credentials!!", be);
      throw new ApiException(ApiCode.BAD_CREDENTIALS, "Bad Credentials!!", be).setHttpStatus(HttpStatus.UNAUTHORIZED);
    }
  }

}

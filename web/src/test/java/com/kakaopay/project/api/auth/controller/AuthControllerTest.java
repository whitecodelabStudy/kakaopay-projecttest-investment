package com.kakaopay.project.api.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.project.api.auth.dto.AuthMemberDto;
import com.kakaopay.project.api.base.controller.BaseControllerTest;
import com.kakaopay.project.api.util.TestUtil;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.web.WebApplication;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
class AuthControllerTest extends BaseControllerTest {

  /**
   * 토큰 발급 테스트 정상처리
   */
  @Test
  @Order(1)
  @Transactional
  void generateToken() throws Exception {
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(100);
    authMemberDto.setPassword("1q2w3e4r");
    authMemberDto.setMemberType("ADMIN");

    this.setHeaders(setMemberIdHeader(String.valueOf(authMemberDto.getMemberId())));

    MvcResult mvcResult = getMockMvc()
        .perform(MockMvcRequestBuilders.post("/auth/authenticate").accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(authMemberDto)).headers(this.getHeaders())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(ApiCode.SUCCESS.getCode()).isEqualTo(apiResponseJson.getResultCode());
  }

  /**
   * 토큰 발급 테스트 인증실패 (비밀번호)
   */
  @Test
  @Order(2)
  @Transactional
  void generateTokenBadCredentials() throws Exception {
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(200);
    authMemberDto.setPassword("!!!!1q2w3e4r");
    authMemberDto.setMemberType("ADMIN");

    this.setHeaders(setMemberIdHeader(String.valueOf(authMemberDto.getMemberId())));

    MvcResult mvcResult = getMockMvc()
        .perform(MockMvcRequestBuilders.post("/auth/authenticate").accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(authMemberDto)).headers(this.getHeaders())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(MockMvcResultHandlers.print()).andReturn();
    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(ApiCode.BAD_CREDENTIALS.getCode()).isEqualTo(apiResponseJson.getResultCode());
  }

  /**
   * 토큰 발급 테스트 사용자 찾지 못함
   */
  @Test
  @Order(3)
  @Transactional
  void generateTokenMemberNotFound() throws Exception {
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(111100);
    authMemberDto.setPassword("1q2w3e4r");
    authMemberDto.setMemberType("ADMIN");

    this.setHeaders(setMemberIdHeader(String.valueOf(authMemberDto.getMemberId())));

    MvcResult mvcResult = getMockMvc()
        .perform(MockMvcRequestBuilders.post("/auth/authenticate").accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(authMemberDto)).headers(this.getHeaders())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(MockMvcResultHandlers.print()).andReturn();
    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(ApiCode.BAD_CREDENTIALS.getCode()).isEqualTo(apiResponseJson.getResultCode());
  }

}

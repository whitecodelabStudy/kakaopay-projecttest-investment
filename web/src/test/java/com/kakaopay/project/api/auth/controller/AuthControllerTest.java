package com.kakaopay.project.api.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.kakaopay.project.api.base.controller.BaseControllerTest;
import com.kakaopay.project.api.auth.dto.AuthMemberDto;
import com.kakaopay.project.api.util.TestUtil;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
public class AuthControllerTest extends BaseControllerTest {

  /**
   * 토큰 발급 테스트 정상처리
   */
  @Test
  public void generateToken() throws Exception {
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(memberId);
    authMemberDto.setMemberType("INVESTOR");
    authMemberDto.setPassword("1q2w3e4r!@");

    assertThat(issueAccessToken(authMemberDto)).isNotEmpty();
  }

  /**
   * 토큰 발급 테스트 인증실패 (비밀번호)
   */
  @Test
  public void generateTokenBadCredentials() throws Exception {
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(memberId);
    authMemberDto.setMemberType("ADMIN");
    authMemberDto.setPassword("test!@");
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/authenticate").accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(authMemberDto))
            .headers(setMemberIdHeader(String.valueOf(authMemberDto.getMemberId())))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(MockMvcResultHandlers.print()).andReturn();
    assertThat(ApiCode.BAD_CREDENTIALS.getCode()).isEqualTo(TestUtil.getApiResponseJson(mvcResult).getResultCode());
  }

  /**
   * 토큰 발급 테스트 사용자 찾지 못함
   */
  @Test
  public void generateTokenMemberNotFound() throws Exception {
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(memberIdFail);
    authMemberDto.setMemberType("ADMIN");
    authMemberDto.setPassword("1q2w3e4r!@");

    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/authenticate").accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(authMemberDto))
            .headers(setMemberIdHeader(String.valueOf(authMemberDto.getMemberId())))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(MockMvcResultHandlers.print()).andReturn();
    assertThat(ApiCode.MEMBER_NOT_FOUND.getCode()).isEqualTo(TestUtil.getApiResponseJson(mvcResult).getResultCode());
  }

}

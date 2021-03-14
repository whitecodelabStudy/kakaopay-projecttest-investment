package com.kakaopay.project.api.auth.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakaopay.project.api.BaseControllerTest;
import com.kakaopay.project.api.auth.dto.AuthMemberDto;
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
    authMemberDto.setMemberId(20191218);
    authMemberDto.setMemberType("INVESTOR");
    authMemberDto.setPassword("1q2w3e4r!@");

    assertThat(issueAccessToken(authMemberDto)).isNotEmpty();
  }

  /**
   * 토큰 발급 테스트 인증실패
   */
  @Test
  public void generateTokenBadCredentials() throws Exception {
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(20191218);
    authMemberDto.setMemberType("ADMIN");
    authMemberDto.setPassword("1q2w3e41r!@");
    mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/authenticate").accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(authMemberDto))
            .headers(setMemberIdHeader(String.valueOf(authMemberDto.getMemberId())))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(MockMvcResultHandlers.print());
  }

  /**
   * 토큰 발급 테스트 사용자 찾지 못함
   */
  @Test
  public void generateTokenMemberNotFound() throws Exception {
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(2019121811);
    authMemberDto.setMemberType("ADMIN");
    authMemberDto.setPassword("1q2w3e41r!@");
    mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/authenticate").accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(authMemberDto))
            .headers(setMemberIdHeader(String.valueOf(authMemberDto.getMemberId())))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(MockMvcResultHandlers.print());
  }

}
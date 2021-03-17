package com.kakaopay.project.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.project.api.auth.dto.AuthMemberDto;
import com.kakaopay.project.api.util.TestUtil;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Disabled
public class BaseControllerTest {

  private static final String AUTH_HEADER = "Authorization";
  private static final String TOKEN_TYPE = "Bearer ";
  protected long memberId = 20_191_218;
  protected long memberIdFail = 120_191_218;

  @Autowired
  protected MockMvc mockMvc;
  protected HttpHeaders headers;

  protected void makeHeader() throws Exception {
    // 테스트 계정 설정.
    AuthMemberDto authMemberDto = settingTestMember();
    // member id header 세팅.
    this.headers = setMemberIdHeader(String.valueOf(authMemberDto.getMemberId()));
    // token 발급.
    String accessToken = issueAccessToken(authMemberDto);
    // 발급받은 token 헤더에 세팅.
    this.headers.set(AUTH_HEADER, TOKEN_TYPE + accessToken);
  }

  protected String issueAccessToken(AuthMemberDto authMemberDto) throws Exception {
    if (this.headers == null) {
      this.headers = setMemberIdHeader(String.valueOf(authMemberDto.getMemberId()));
    }
    // token 발급
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/authenticate").accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(authMemberDto)).headers(this.headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(ApiCode.SUCCESS.getCode()).isEqualTo(apiResponseJson.getResultCode());
    return (String) ((LinkedHashMap) apiResponseJson.getResponse().get(0)).get("accessToken");
  }

  protected AuthMemberDto settingTestMember() {
    // token 발급받을 계정 세팅.
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(memberId);
    authMemberDto.setMemberType("INVESTOR");
    authMemberDto.setPassword("1q2w3e4r");
    return authMemberDto;
  }

  protected HttpHeaders setMemberIdHeader(String memberId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("X-USER-ID", memberId);
    return headers;
  }

}
